package world.gregs.voidps.network.encode

import world.gregs.voidps.buffer.write.writeByteInverse
import world.gregs.voidps.buffer.write.writeByteSubtract
import world.gregs.voidps.buffer.write.writeIntMiddle
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.network.Encoder
import world.gregs.voidps.network.GameOpcodes.SKILL_LEVEL

/**
 * @author GregHib <greg@gregs.world>
 * @since July 27, 2020
 */
class SkillLevelEncoder : Encoder(SKILL_LEVEL) {

    /**
     * Updates the players skill level & experience
     * @param skill The skills id
     * @param level The current players level
     * @param experience The current players experience
     */
    fun encode(
        player: Player,
        skill: Int,
        level: Int,
        experience: Int
    ) = player.send(6) {
        writeByteSubtract(level)
        writeByteInverse(skill)
        writeIntMiddle(experience)
    }
}