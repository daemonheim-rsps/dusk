package world.gregs.voidps.network.decode

import io.ktor.utils.io.core.*
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.Decoder

class ResumeObjDialogueDecoder : Decoder(2) {

    override fun decode(player: Player, packet: ByteReadPacket) {
        handler?.resumeObjectDialogue(
            player = player,
            value = packet.readShort().toInt()
        )
    }

}