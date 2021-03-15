package world.gregs.voidps.network.decode

import world.gregs.voidps.buffer.read.Reader
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.Decoder

class IgnoreListAddDecoder : Decoder(BYTE) {

    override fun decode(player: Player, packet: Reader) {
        handler?.addIgnore(
            player = player,
            packet.readString(),
            packet.readBoolean()
        )
    }

}