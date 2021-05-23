package world.gregs.voidps.bot

import world.gregs.voidps.engine.entity.character.player.Bot

data class Task(
    val block: suspend Bot.() -> Unit,
    val unlimited: Boolean = false,
    val requirements: Collection<Bot.() -> Boolean> = emptySet()
)