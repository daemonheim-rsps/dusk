package rs.dusk.network.codec.game.decode

import io.netty.channel.ChannelHandlerContext
import rs.dusk.buffer.Endian
import rs.dusk.buffer.Modifier
import rs.dusk.buffer.read.Reader
import rs.dusk.network.codec.Decoder

class InterfaceOnPlayerDecoder : Decoder(1) {

    override fun decode(context: ChannelHandlerContext, packet: Reader) {
        handler?.interfaceOnPlayer(
            context,
            packet.readShort(order = Endian.LITTLE),
            packet.readInt(order = Endian.LITTLE),
            packet.readShort(),
            packet.readBoolean(Modifier.SUBTRACT),
            packet.readShort(Modifier.ADD, Endian.LITTLE)
        )
    }

}