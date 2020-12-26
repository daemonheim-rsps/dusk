package rs.dusk.client.update.encode

import rs.dusk.core.network.buffer.Endian
import rs.dusk.core.network.buffer.Modifier
import rs.dusk.core.network.buffer.write.Writer
import rs.dusk.game.entity.character.update.VisualEncoder
import rs.dusk.game.entity.character.update.visual.ColourOverlay

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class ColourOverlayEncoder(private val npc : Boolean, mask : Int) : VisualEncoder<ColourOverlay>(mask) {
	
	override fun encode(writer : Writer, visual : ColourOverlay) {
		val (delay, duration, colour) = visual
		writer.apply {
			val hue = colour and 0xFF
			val saturation = colour shr 8 and 0xFF
			val luminance = colour shr 16 and 0xFF
			val multiplier = colour shr 24 and 0xFF
			writeByte(hue, if (npc) Modifier.ADD else Modifier.SUBTRACT)
			writeByte(saturation, Modifier.SUBTRACT)
			writeByte(luminance, if (npc) Modifier.NONE else Modifier.INVERSE)
			writeByte(multiplier, if (npc) Modifier.SUBTRACT else Modifier.NONE)
			writeShort(delay, if (npc) Modifier.NONE else Modifier.ADD, Endian.LITTLE)
			writeShort(duration)
		}
	}
	
}