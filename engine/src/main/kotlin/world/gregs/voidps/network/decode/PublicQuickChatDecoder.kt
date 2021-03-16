package world.gregs.voidps.network.decode

import io.ktor.utils.io.core.*
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.Decoder

class PublicQuickChatDecoder : Decoder(BYTE) {

    override fun decode(player: Player, packet: ByteReadPacket) {
        handler?.publicQuickChat(
            player = player,
            script = packet.readByte().toInt(),
            file = packet.readUShort().toInt(),
            data = packet.readBytes(packet.remaining.toInt())
        )
    }

}