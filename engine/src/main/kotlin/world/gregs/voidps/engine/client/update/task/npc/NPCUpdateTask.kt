package world.gregs.voidps.engine.client.update.task.npc

import world.gregs.voidps.buffer.write.Writer
import world.gregs.voidps.engine.client.Sessions
import world.gregs.voidps.engine.entity.character.CharacterTrackingSet
import world.gregs.voidps.engine.entity.character.npc.NPC
import world.gregs.voidps.engine.entity.character.npc.teleporting
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.engine.entity.character.player.Players
import world.gregs.voidps.engine.entity.character.update.LocalChange
import world.gregs.voidps.engine.entity.character.update.visual.npc.getTurn
import world.gregs.voidps.engine.event.Priority.NPC_UPDATE
import world.gregs.voidps.engine.tick.task.EntityTask
import world.gregs.voidps.network.codec.game.encode.NPCUpdateEncoder

/**
 * @author GregHib <greg@gregs.world>
 * @since May 12, 2020
 */
class NPCUpdateTask(
    override val entities: Players,
    val sessions: Sessions,
    private val npcUpdateEncoder: NPCUpdateEncoder
) : EntityTask<Player>(NPC_UPDATE) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun runAsync(player: Player) {
        if (!sessions.contains(player)) {
            return
        }

        val viewport = player.viewport
        val npcs = viewport.npcs

        val writer = viewport.npcChanges
        val updates = viewport.npcUpdates

        processLocals(writer, updates, npcs)
        processAdditions(writer, updates, player, npcs)

//        npcUpdateEncoder.encode(player, writer, updates)
    }

    fun processLocals(
        sync: Writer,
        updates: Writer,
        set: CharacterTrackingSet<NPC>
    ) {
        sync.startBitAccess()
        sync.writeBits(8, set.current.size)
        for (npc in set.current) {
            val remove = set.remove.contains(npc)
            val change = if (remove) LocalChange.Remove else npc.change

            if (change == null) {
                sync.writeBits(1, false)
                continue
            }

            sync.writeBits(1, true)
            sync.writeBits(2, change.id)

            when (change) {
                LocalChange.Walk -> {
                    sync.writeBits(3, npc.walkDirection)
                    sync.writeBits(1, npc.visuals.update != null)
                }
                LocalChange.Crawl -> {
                    sync.writeBits(1, false)
                    sync.writeBits(3, npc.walkDirection)
                    sync.writeBits(1, npc.visuals.update != null)
                }
                LocalChange.Run -> {
                    sync.writeBits(1, true)
                    sync.writeBits(3, npc.walkDirection)
                    sync.writeBits(3, npc.runDirection)
                    sync.writeBits(1, npc.visuals.update != null)
                }
                else -> {
                }
            }
            if (!remove) {
                updates.writeBytes(npc.visuals.update ?: continue)
            }
        }

        sync.finishBitAccess()
    }

    fun processAdditions(
        sync: Writer,
        updates: Writer,
        client: Player,
        set: CharacterTrackingSet<NPC>
    ) {
        for (npc in set.add) {
            val delta = npc.tile.delta(client.tile)
            sync.writeBits(15, npc.index)
            sync.writeBits(3, (npc.getTurn().direction shr 11) - 4)
            sync.writeBits(1, npc.visuals.addition != null)
            sync.writeBits(5, delta.y + if (delta.y < 15) 32 else 0)
            sync.writeBits(2, npc.tile.plane)
            sync.writeBits(15, npc.id)
            sync.writeBits(5, delta.x + if (delta.x < 15) 32 else 0)
            sync.writeBits(1, npc.teleporting)
            updates.writeBytes(npc.visuals.addition ?: continue)
        }
        sync.writeBits(15, -1)
        sync.finishBitAccess()
    }

}