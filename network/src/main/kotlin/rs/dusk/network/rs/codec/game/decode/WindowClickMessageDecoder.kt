package rs.dusk.network.rs.codec.game.decode

import rs.dusk.buffer.Endian
import rs.dusk.buffer.Modifier
import rs.dusk.core.network.codec.packet.access.PacketReader
import rs.dusk.network.rs.codec.game.GameMessageDecoder
import rs.dusk.network.rs.codec.game.decode.message.WindowClickMessage

class WindowClickMessageDecoder : GameMessageDecoder<WindowClickMessage>(6) {

    override fun decode(packet: PacketReader) =
        WindowClickMessage(
            packet.readShort(Modifier.ADD, Endian.LITTLE),
            packet.readInt(order = Endian.MIDDLE)
        )

}