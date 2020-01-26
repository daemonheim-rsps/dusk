package org.redrune.network

import io.netty.channel.Channel
import io.netty.util.AttributeKey
import mu.KotlinLogging
import org.redrune.network.codec.handshake.message.ResponseValue
import org.redrune.network.codec.handshake.message.VersionMessage
import org.redrune.network.codec.update.message.VersionResponseMessage
import org.redrune.network.message.Message
import org.redrune.tools.constants.NetworkConstants
import java.net.InetSocketAddress

/**
 * This class represents the network session from the client (player) to the server
 *
 * @author Tyluur <contact@kiaira.tech>
 * @since 2020-01-07
 */
open class Session(
    /**
     * The channel for the connection
     */
    var channel: Channel
) {

    private val logger = KotlinLogging.logger {}

    /**
     * When a message is received, this function is invoked
     */
    open fun messageReceived(msg: Any) {
        println("REceived msg $msg")
        if (msg is VersionMessage) {
            val version = msg.version

            if (version == NetworkConstants.CLIENT_MAJOR_BUILD) {
                send(VersionResponseMessage(ResponseValue.SUCCESSFUL))
            } else {
                send(VersionResponseMessage(ResponseValue.OUT_OF_DATE))
            }
        }
        send(msg)
    }

    /**
     * When a message is received, this function is invoked
     */
    open fun messageReceived(msg: Message) {
        println("Received msg $msg")
        if (msg is VersionMessage) {
            val version = msg.version

            if (version == NetworkConstants.CLIENT_MAJOR_BUILD) {
                send(VersionResponseMessage(ResponseValue.SUCCESSFUL))
            } else {
                send(VersionResponseMessage(ResponseValue.OUT_OF_DATE))
            }
        }
    }

    /**
     * Sends a message to the channel by [Channel.writeAndFlush]
     */
    fun send(msg: Any) {
        channel.writeAndFlush(msg)
    }

    fun getHost(): String = (channel.remoteAddress() as? InetSocketAddress)?.address?.hostAddress
        ?: NetworkConstants.LOCALHOST

    fun printPipeline() {
        val pipelineHandlers: StringBuilder = StringBuilder("")
        channel.pipeline().forEach { pipelineHandlers.append("${it.value.javaClass.simpleName}, ") }
        println(pipelineHandlers)
    }

    companion object {
        /**
         * The attribute that maps to the session of a channel
         */
        val SESSION_KEY: AttributeKey<Session> = AttributeKey.valueOf<Session>("session.key")

        /**
         * The attribute that maps to the encryption key
         */
        val ENCRYPTION_KEY = AttributeKey.valueOf<Int>("encryption.key")!!
    }

}