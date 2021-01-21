package world.gregs.void.world.community.trade.lend

import com.github.michaelbull.logging.InlineLogger
import world.gregs.void.engine.entity.character.get
import world.gregs.void.engine.entity.character.has
import world.gregs.void.engine.entity.character.player.Player
import world.gregs.void.engine.entity.character.player.PlayerRegistered
import world.gregs.void.engine.entity.character.set
import world.gregs.void.engine.event.then
import world.gregs.void.network.codec.game.encode.message
import world.gregs.void.world.community.trade.lend.Loan.returnLoan
import world.gregs.void.world.community.trade.lend.Loan.startBorrowTimer
import world.gregs.void.world.community.trade.lend.Loan.startLendTimer
import world.gregs.void.world.interact.entity.player.spawn.logout.Logout

/**
 * Reschedule timers on player login
 * On logout return items borrowed or lent until logout
 */

val logger = InlineLogger()

PlayerRegistered then {
    startLendTimer(player)
    startBorrowTimer(player)
}

Logout then {
    if (!player.has("borrow_timeout") && player.has("borrowed_item")) {
        returnLoan(player)
        val partner: Player? = player["borrowed_from"]
        if (partner == null) {
            logger.error { "Unable to find borrowed item partner for $player" }
        } else {
            reset(player, partner)
            partner.message("The item you lent has been returned to your collection box.")
        }
    }
    if (!player.has("lend_timeout") && player.has("lent_item")) {
        val partner: Player? = player["lent_to"]
        if (partner == null) {
            logger.error { "Unable to find lent item partner for $player" }
            return@then
        }
        reset(partner, player)
        partner.message("The item you borrowed has been returned to its owner.")
    }
}

fun reset(borrower: Player, lender: Player) {
    val time = System.currentTimeMillis() - 1
    lender["lend_timeout", true] = time
    borrower["borrow_timeout", true] = time
}