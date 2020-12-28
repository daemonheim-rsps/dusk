package rs.dusk.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.core.io.crypto.IsaacKeyPair
import rs.dusk.core.network.codec.message.MessageHandler
import rs.dusk.core.network.codec.message.decode.OpcodeMessageDecoder
import rs.dusk.core.network.codec.message.encode.GenericMessageEncoder
import rs.dusk.core.network.codec.packet.access.PacketBuilder
import rs.dusk.core.network.codec.packet.decode.RS2PacketDecoder
import rs.dusk.core.network.codec.setCodec
import rs.dusk.core.network.model.session.getSession
import rs.dusk.core.utility.replace
import rs.dusk.engine.client.Sessions
import rs.dusk.engine.entity.Registered
import rs.dusk.engine.entity.character.player.GameLoginInfo
import rs.dusk.engine.entity.character.player.PlayerRegistered
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.map.region.RegionLogin
import rs.dusk.engine.task.TaskExecutor
import rs.dusk.engine.task.sync
import rs.dusk.network.rs.codec.game.GameCodec
import rs.dusk.network.rs.codec.login.LoginCodec
import rs.dusk.network.rs.codec.login.encode.message.GameLoginConnectionResponseMessage
import rs.dusk.network.rs.codec.login.encode.message.GameLoginDetails
import rs.dusk.utility.inject
import rs.dusk.world.interact.entity.player.spawn.login.Login
import rs.dusk.world.interact.entity.player.spawn.login.LoginResponse

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 18, 2020
 */
class GameLoginMessageHandler : MessageHandler() {

    val logger = InlineLogger()
    val sessions: Sessions by inject()
    val bus: EventBus by inject()
    val executor: TaskExecutor by inject()

    override fun loginGame(
        context: ChannelHandlerContext,
        username: String,
        password: String,
        isaacKeys: IntArray,
        mode: Int,
        width: Int,
        height: Int,
        antialias: Int,
        settings: String,
        affiliate: Int,
        session: Int,
        os: Int,
        is64Bit: Int,
        versionType: Int,
        vendorType: Int,
        javaRelease: Int,
        javaVersion: Int,
        javaUpdate: Int,
        isUnsigned: Int,
        heapSize: Int,
        processorCount: Int,
        totalMemory: Int
    ) {
        val channel = context.channel()
        val pipeline = context.pipeline()
        val keyPair = IsaacKeyPair(isaacKeys)

        channel.setCodec(LoginCodec)
        pipeline.replace("message.encoder", GenericMessageEncoder(PacketBuilder(sized = true)))

        val playerSession = context.channel().getSession()

        val callback: (LoginResponse) -> Unit = { response ->
            if (response is LoginResponse.Success) {
                val player = response.player
                pipeline.writeAndFlush(GameLoginDetails(2, player.index, username))

                with(pipeline) {
                    replace("packet.decoder", RS2PacketDecoder(keyPair.inCipher))
                    replace("message.decoder", OpcodeMessageDecoder())
                    replace("message.encoder", GenericMessageEncoder(PacketBuilder(keyPair.outCipher)))
                }

                executor.sync {
                    channel.setCodec(GameCodec)

                    bus.emit(RegionLogin(player))
                    bus.emit(PlayerRegistered(player))
                    player.start()
                    bus.emit(Registered(player))
                }
            } else {
                pipeline.writeAndFlush(GameLoginConnectionResponseMessage(response.code))
            }
        }

        executor.sync {
            bus.emit(
                Login(
                    username,
                    playerSession,
                    callback,
                    GameLoginInfo(username, password, isaacKeys, mode, width, height, antialias, settings, affiliate, session, os, is64Bit, versionType, vendorType, javaRelease, javaVersion, javaUpdate, isUnsigned, heapSize, processorCount, totalMemory)
                )
            )
        }
    }
}