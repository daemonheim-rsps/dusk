package rs.dusk.network.codec.game.decode

import io.netty.channel.ChannelHandlerContext
import rs.dusk.buffer.read.Reader
import rs.dusk.network.codec.Decoder
import rs.dusk.network.packet.PacketSize.BYTE

class ClanSettingsUpdateDecoder : Decoder(BYTE) {

    override fun decode(context: ChannelHandlerContext, packet: Reader) {
        handler?.updateClanSettings(
            context,
            packet.readShort(),
            packet.readString()
        )
    }

}