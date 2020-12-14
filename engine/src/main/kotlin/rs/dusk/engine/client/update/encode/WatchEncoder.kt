package rs.dusk.engine.client.update.encode

import rs.dusk.buffer.Endian
import rs.dusk.buffer.Modifier
import rs.dusk.buffer.write.Writer
import rs.dusk.engine.entity.character.update.VisualEncoder
import rs.dusk.engine.entity.character.update.visual.Watch

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class WatchEncoder(private val npc: Boolean, mask: Int) : VisualEncoder<Watch>(mask) {

    override fun encode(writer: Writer, visual: Watch) {
        writer.writeShort(
            visual.index,
            if (npc) Modifier.NONE else Modifier.ADD,
            if (npc) Endian.LITTLE else Endian.BIG
        )
    }

}