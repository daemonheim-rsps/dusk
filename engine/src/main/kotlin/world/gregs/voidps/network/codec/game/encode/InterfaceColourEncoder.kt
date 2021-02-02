package world.gregs.voidps.network.codec.game.encode

import world.gregs.voidps.buffer.Endian
import world.gregs.voidps.buffer.Modifier
import world.gregs.voidps.buffer.write.writeInt
import world.gregs.voidps.buffer.write.writeShort
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.codec.Encoder
import world.gregs.voidps.network.codec.game.GameOpcodes.INTERFACE_COLOUR
import world.gregs.voidps.network.codec.game.GameOpcodes.INTERFACE_SPRITE

/**
 * @author GregHib <greg@gregs.world>
 * @since August 2, 2020
 */
class InterfaceColourEncoder : Encoder(INTERFACE_COLOUR) {

    /**
     * Sends a sprite to a interface component
     * @param id The id of the parent interface
     * @param component The index of the component
     * @param sprite The sprite id
     */
    fun encode(
        player: Player,
        id: Int,
        component: Int
    ) = player.send(6) {
        writeInt(id shl 16 or component)
        val r = 255
        val g = 0
        val b = 0
        writeShort((r shl 10) + (g shl 5) + b, Modifier.ADD)
    }
}