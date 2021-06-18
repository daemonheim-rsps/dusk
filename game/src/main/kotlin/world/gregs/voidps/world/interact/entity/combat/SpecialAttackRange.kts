import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.engine.entity.character.player.skill.Skill
import world.gregs.voidps.engine.event.Priority
import world.gregs.voidps.engine.event.on
import world.gregs.voidps.world.interact.entity.combat.CombatSwing
import world.gregs.voidps.world.interact.entity.combat.HitDamageModifier
import world.gregs.voidps.world.interact.entity.player.combat.special.specialAttack
import kotlin.math.floor

fun isWeaponOutlier(special: Boolean, name: String?): Boolean = (special && name?.startsWith("magic") == true || name == "seercull" || name == "rune_thrownaxe") || name == "ogre_bow"

on<HitDamageModifier>({ player -> type == "range" && isWeaponOutlier(player.specialAttack, weapon?.name) }, Priority.HIGH) { player: Player ->
    damage = 0.5 + (player.levels.get(Skill.Range) + 10) * strengthBonus / 64
    if (weapon?.name == "rune_thrownaxe" || (weapon?.name == "magic_shortbow" && target is Player)) {
        damage += 1.0
    }
}

on<HitDamageModifier>({ player -> type == "range" && player.specialAttack && weapon?.name?.endsWith("morrigans_throwing_axe") == true }, Priority.LOW) { _: Player ->
    damage = floor(damage * 1.2)
}

on<CombatSwing>({ player -> player.specialAttack }, Priority.LOWEST) { player: Player ->
    player.specialAttack = false
}