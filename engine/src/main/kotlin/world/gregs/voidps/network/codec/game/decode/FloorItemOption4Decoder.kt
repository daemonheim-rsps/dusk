package world.gregs.voidps.network.codec.game.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.voidps.buffer.DataType
import world.gregs.voidps.buffer.Endian
import world.gregs.voidps.buffer.Modifier
import world.gregs.voidps.buffer.read.Reader
import world.gregs.voidps.network.codec.Decoder

class FloorItemOption4Decoder : Decoder(7) {

    override fun decode(context: ChannelHandlerContext, packet: Reader) {
        handler?.floorItemOption(
            context = context,
            run = packet.readBoolean(Modifier.SUBTRACT),
            x = packet.readShort(Modifier.ADD),
            y = packet.readShort(order = Endian.LITTLE),
            id = packet.readShort(),
            option = 4
        )
    }

}