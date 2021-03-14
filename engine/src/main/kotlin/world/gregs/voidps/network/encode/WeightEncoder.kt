package world.gregs.voidps.network.encode

import world.gregs.voidps.buffer.write.writeShort
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.Encoder
import world.gregs.voidps.network.GameOpcodes.PLAYER_WEIGHT

/**
 * @author GregHib <greg@gregs.world>
 * @since September 13, 2020
 */
class WeightEncoder : Encoder(PLAYER_WEIGHT) {

    /**
     * Updates player weight for equipment screen
     */
    fun encode(
        player: Player,
        weight: Int
    ) = player.send(2) {
        writeShort(weight)
    }
}