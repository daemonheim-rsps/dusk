package rs.dusk.network.codec.game.encode.message

import rs.dusk.network.codec.game.GameMessage

/**
 * A variable player; also known as "Config", known in the client as "clientvarp"
 * @param id The config id
 * @param value The value to pass to the config
 * @param large Whether to encode value with integer rather than short (optional - calculated automatically)
 */
data class VarpMessage(val id: Int, val value: Int, val large: Boolean = value !in Byte.MIN_VALUE..Byte.MAX_VALUE) :
    GameMessage