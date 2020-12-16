package rs.dusk.tools.map.view.draw

import rs.dusk.tools.map.view.graph.NavigationGraph
import java.awt.Color
import java.awt.Graphics
import kotlin.math.max
import kotlin.math.min

class AreaPointConnector(private val view: MapView, private val nav: NavigationGraph) {
    private var linkX = -1
    private var linkY = -1
    private var linkEndX = -1
    private var linkEndY = -1
    private var draw = false

    fun update(mapStartX: Int, mapStartY: Int, mouseX: Int, mouseY: Int) {
        draw = true
        repaint()
        val viewX = view.mapToViewX(mapStartX) + view.mapToImageX(1) / 2
        val viewY = view.mapToViewY(mapStartY) + view.mapToImageY(1) / 2
        linkX = viewX
        linkY = viewY
        linkEndX = mouseX
        linkEndY = mouseY
        repaint()
    }

    private fun repaint() {
        view.repaint(min(linkX, linkEndX), min(linkY, linkEndY), max(linkX, linkEndX), max(linkY, linkEndY))
    }

    fun reset() {
        if (draw) {
            if (view.bounds.contains(linkEndX, linkEndY)) {
                val mapX = view.viewToMapX(linkX)
                val mapY = view.flipMapY(view.viewToMapY(linkY))
                val endX = view.viewToMapX(linkEndX)
                val endY = view.flipMapY(view.viewToMapY(linkEndY))

                val start = nav.getPointOrNull(mapX, mapY, 0) ?: nav.addArea(mapX, mapY, 0).points.first()
                val end = nav.getPointOrNull(endX, endY, 0)
                if (end == null) {
                    nav.addPoint(start, endX, endY)
                } else {
                    nav.removePoint(start.area, end)
                }
            }
            draw = false
            repaint()
        }
    }

    fun draw(g: Graphics) {
        if (draw) {
            g.color = Color.YELLOW
            g.drawLine(linkX, linkY, linkEndX, linkEndY)
        }
    }
}