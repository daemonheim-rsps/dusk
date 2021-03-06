package world.gregs.voidps.network.codec.game.encode

import world.gregs.voidps.buffer.write.writeByteAdd
import world.gregs.voidps.buffer.write.writeIntLittle
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.codec.Encoder
import world.gregs.voidps.network.codec.game.GameOpcodes.INTERFACE_COMPONENT_VISIBILITY

/**
 * @author GregHib <greg@gregs.world>
 * @since August 2, 2020
 */
class InterfaceVisibilityEncoder : Encoder(INTERFACE_COMPONENT_VISIBILITY) {

    /**
     * Toggles a interface component
     * @param id The parent interface id
     * @param component The component to change
     * @param hide Visibility
     */
    fun encode(
        player: Player,
        id: Int,
        component: Int,
        hide: Boolean
    ) = player.send(5) {
        writeByteAdd(hide)
        writeIntLittle(id shl 16 or component)
    }
}