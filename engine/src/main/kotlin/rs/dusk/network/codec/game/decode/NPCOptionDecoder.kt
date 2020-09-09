package rs.dusk.network.codec.game.decode

import io.netty.channel.ChannelHandlerContext
import rs.dusk.buffer.Modifier
import rs.dusk.buffer.read.Reader
import rs.dusk.network.codec.Decoder

class NPCOptionDecoder(private val index: Int) : Decoder(3) {

    override fun decode(context: ChannelHandlerContext, packet: Reader) {
        handler?.npcOption(
            context,
            packet.readBoolean(Modifier.ADD),
            packet.readShort(Modifier.ADD),
            index + 1
        )
    }

}