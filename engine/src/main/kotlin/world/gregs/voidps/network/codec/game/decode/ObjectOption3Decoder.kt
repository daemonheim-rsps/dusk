package world.gregs.voidps.network.codec.game.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.voidps.buffer.DataType
import world.gregs.voidps.buffer.Endian
import world.gregs.voidps.buffer.Modifier
import world.gregs.voidps.buffer.read.Reader
import world.gregs.voidps.network.codec.Decoder

class ObjectOption3Decoder : Decoder(7) {

    override fun decode(context: ChannelHandlerContext, packet: Reader) {
        handler?.objectOption(
            context = context,
            y = packet.readShort(Modifier.ADD),
            objectId = packet.readShort(Modifier.ADD, Endian.LITTLE),
            x = packet.readShort(order = Endian.LITTLE),
            run = packet.readBoolean(Modifier.ADD),
            option = 3
        )
    }

}