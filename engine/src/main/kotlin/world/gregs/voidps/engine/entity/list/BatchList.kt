package world.gregs.voidps.engine.entity.list

import world.gregs.voidps.engine.entity.Entity
import world.gregs.voidps.engine.map.Tile
import world.gregs.voidps.engine.map.chunk.Chunk

interface BatchList<T : Entity> {

    val chunks: MutableMap<Chunk, MutableList<T>>

    fun add(entity: T) = chunks.getOrPut(entity.tile.chunk) { mutableListOf() }.add(entity)

    fun remove(entity: T): Boolean {
        val tile = chunks[entity.tile.chunk] ?: return false
        return tile.remove(entity)
    }

    fun clear(chunk: Chunk) {
        chunks.remove(chunk)
    }

    operator fun get(tile: Tile): List<T> = get(tile.chunk)

    operator fun get(chunk: Chunk): List<T> = chunks[chunk] ?: emptyList()
}