package rs.dusk.engine.entity.list.npc

import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import rs.dusk.engine.entity.model.NPC
import rs.dusk.engine.model.Tile

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
class NPCList : NPCs {
    override val delegate: SetMultimap<Tile, NPC> = HashMultimap.create()
}