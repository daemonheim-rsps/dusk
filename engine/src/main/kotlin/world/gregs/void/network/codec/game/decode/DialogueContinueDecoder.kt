package world.gregs.void.network.codec.game.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.void.buffer.Endian
import world.gregs.void.buffer.Modifier
import world.gregs.void.buffer.read.Reader
import world.gregs.void.network.codec.Decoder

class DialogueContinueDecoder : Decoder(6) {

    override fun decode(context: ChannelHandlerContext, packet: Reader) {
        handler?.continueDialogue(
            context,
            packet.readInt(Modifier.INVERSE, Endian.MIDDLE),
            packet.readShort(Modifier.ADD, Endian.LITTLE)
        )
    }

}