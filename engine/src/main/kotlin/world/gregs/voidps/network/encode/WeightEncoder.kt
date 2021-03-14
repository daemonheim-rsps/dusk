package world.gregs.voidps.network.encode

import io.ktor.utils.io.*
import world.gregs.voidps.network.Client
import world.gregs.voidps.network.GameOpcodes.PLAYER_WEIGHT

/**
 * Updates player weight for equipment screen
 */
fun Client.weight(
    weight: Int
) = send(PLAYER_WEIGHT, 2) {
    writeShort(weight)
}