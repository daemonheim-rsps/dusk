package org.redrune.network.codec.login.decode.impl

import org.redrune.cache.Cache
import org.redrune.core.network.packet.PacketMetaData
import org.redrune.core.network.packet.PacketType
import org.redrune.core.network.packet.access.PacketReader
import org.redrune.network.codec.login.decode.LoginHeader
import org.redrune.network.codec.login.decode.LoginMessageDecoder
import org.redrune.network.codec.login.decode.message.LobbyLoginMessage
import org.redrune.tools.constants.ServiceOpcodes

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
@PacketMetaData(opcodes = [ServiceOpcodes.LOBBY_LOGIN], length = PacketType.VARIABLE_LENGTH_SHORT)
class LobbyLoginMessageDecoder : LoginMessageDecoder<LobbyLoginMessage>() {
    override fun decode(packet: PacketReader): LobbyLoginMessage {
        val triple = LoginHeader.decode(packet)
        val password = triple.second!!
        val isaacKeys = triple.third!!

        val username = packet.readString()
        val highDefinition = packet.readBoolean()
        val resizeable = packet.readBoolean()
        packet.skip(24)
        val settings = packet.readString()
        val affiliate = packet.readInt()
        val crcMap = mutableMapOf<Int, Pair<Int, Int>>()

        for (index in 0..35) {
            val indexCrc = Cache.index(index).crc
            val clientCrc = packet.readInt()
            crcMap[index] = Pair(indexCrc, clientCrc)
        }
        return LobbyLoginMessage(username, password, highDefinition, resizeable, settings, affiliate, isaacKeys, crcMap)
    }
}