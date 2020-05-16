import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rs.dusk.engine.client.LoginQueue
import rs.dusk.engine.entity.factory.PlayerFactory
import rs.dusk.engine.entity.list.player.Players
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.event.then
import rs.dusk.engine.event.where
import rs.dusk.engine.model.entity.Direction
import rs.dusk.engine.model.entity.index.Move
import rs.dusk.engine.model.entity.index.player.Player
import rs.dusk.engine.model.entity.index.player.PlayerMoveType
import rs.dusk.engine.model.entity.index.player.command.Command
import rs.dusk.engine.model.entity.index.update.visual.*
import rs.dusk.engine.model.entity.index.update.visual.player.*
import rs.dusk.engine.model.world.Tile
import rs.dusk.utility.get
import rs.dusk.utility.inject
import java.util.concurrent.atomic.AtomicInteger

val factory: PlayerFactory by inject()
val players: Players by inject()
val login: LoginQueue by inject()

val botCounter = AtomicInteger(0)

Command where { prefix == "bot" } then {
    println("Bot command")
    runBlocking {
        try {
            val radius = 22
            (-radius..radius).flatMap { x ->
                (-radius..radius).map { y ->
                    factory.spawn(
                        "Bot ${botCounter.getAndIncrement()}",
                        player.tile.add(x, y)
                    )
                }
            }.forEach {
                val bot = it.await()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

Command where { prefix == "kill" } then {
    players.indexed.forEachIndexed { index, bot ->
        if (bot != null && bot.name.startsWith("Bot")) {
            players.remove(bot.tile, bot)
            players.remove(bot.tile.chunk, bot)
        }
    }
    GlobalScope.launch {
        delay(600)
        players.indexed.forEachIndexed { index, bot ->
            if (bot != null && bot.name.startsWith("Bot")) {
                players.indexed[index] = null
            }
        }
    }
}

Command where { prefix == "players" } then {
    println("Players: ${players.indexed.filterNotNull().size}")
}

Command where { prefix == "under" } then {
    players[player.tile]?.filterNotNull()?.forEach {
        println("$it - ${player.viewport.players.current.contains(it)}")
    }
}

Command where { prefix == "anim" } then {
    player.setAnimation(content.toInt())// 863
}

Command where { prefix == "gfx" } then {
    val id = content.toInt()
    player.setGraphic(id)// 93
}

Command where { prefix == "tfm" || prefix == "transform" } then {
    player.transform = content.toInt()
}

Command where { prefix == "overlay" } then {
    player.setColourOverlay(-2108002746, 10, 100)
}

Command where { prefix == "chat" } then {
    player.forceChat = "Testing"
}

Command where { prefix == "move" } then {
    player.setForceMovement(Tile(0, -2), 120, startDelay = 60, direction = Direction.SOUTH)
}

Command where { prefix == "hit" } then {
    player.addHit(
        Hit(
            10,
            Hit.Mark.Healed,
            255
        )
    )
}

Command where { prefix == "time" } then {
    player.setTimeBar(true, 0, 60, 1)
}

Command where { prefix == "watch" } then {
    val bot = players.indexed.firstOrNull { it != null && it.name.startsWith("Bot") }
    if (bot != null) {
        player.watch(bot)
    }
}

Command where { prefix == "mate" } then {
    val bot = players.indexed.firstOrNull { it != null && it.name.startsWith("Bot") }
    bot?.clanmate = true
}

Command where { prefix == "face" } then {
    val parts = content.split(" ")
    player.face(parts[0].toInt(), parts[1].toInt())
}

Command where { prefix == "hide" } then {
    player.minimapHighlight = !player.minimapHighlight
}

Command where { prefix == "walk" } then {
    player.movementType = PlayerMoveType.Walk
//    player.temporaryMoveType = PlayerMoveType.Walk
    val direction = Direction.NORTH
    player.movement.walkStep = direction
    player.movement.delta = direction.delta
    move(player, player.tile.add(direction.delta))
}

Command where { prefix == "run" } then {
    player.movementType = PlayerMoveType.Run
//    player.temporaryMoveType = PlayerMoveType.Run
    player.movement.walkStep = Direction.NORTH
    player.movement.runStep = Direction.NORTH_EAST
    player.movement.delta = player.movement.walkStep.delta.add(player.movement.runStep.delta)
    move(player, player.tile.add(player.movement.delta))
}

fun move(player: Player, tile: Tile) {
    player.movement.lastTile = player.tile
    players.remove(player.tile, player)
    players.remove(player.tile.chunk, player)
    player.tile = tile
    players[player.tile] = player
    players[player.tile.chunk] = player
}

Command where { prefix == "tele" || prefix == "tp" } then {
    if (content.contains(",")) {
        val params = content.split(",")
        val plane = params[0].toInt()
        val x = params[1].toInt() shl 6 or params[3].toInt()
        val y = params[2].toInt() shl 6 or params[4].toInt()
        player.movement.delta = Tile(
            x - player.tile.x,
            y - player.tile.y,
            plane - player.tile.plane
        )
        player.movementType = PlayerMoveType.Teleport
        move(player, Tile(x, y, plane))
        val bus: EventBus = get()
        bus.emit(
            Move(
                player,
                player.tile,
                player.movement.lastTile
            )
        )
    }
}