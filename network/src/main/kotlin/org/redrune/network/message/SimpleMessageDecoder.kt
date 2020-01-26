package org.redrune.network.message

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import mu.KotlinLogging
import org.redrune.network.packet.Packet

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since January 24, 2020 5:46 p.m.
 */
class SimpleMessageDecoder : MessageToMessageDecoder<Packet>() {

    private val logger = KotlinLogging.logger {}

    @Suppress("UNCHECKED_CAST")
    override fun decode(ctx: ChannelHandlerContext, msg: Packet, out: MutableList<Any>) {/*
        val decoder = ctx.channel().attr(Session.SESSION_KEY).get().codec.decoder(msg.opcode) as? MessageDecoder<Message>

        if (decoder == null) {
            logger.info { "Unable to find decoder for [packet=$msg]" }
            return
        }
        out.add(decoder.decode(PacketReader(msg), ctx))*/
    }

}
