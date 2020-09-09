package rs.dusk.handle

import com.github.michaelbull.logging.InlineLogger
import io.netty.channel.ChannelHandlerContext
import rs.dusk.engine.client.Sessions
import rs.dusk.network.codec.Handler
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 05, 2020
 */
class RegionLoadedHandler : Handler() {

    val logger = InlineLogger()
    val sessions: Sessions by inject()

    override fun regionLoaded(context: ChannelHandlerContext) {
        val session = context.channel()
        val player = sessions.get(session) ?: return
        player.viewport.loaded = true
    }

}