package world.gregs.voidps.network.decode

import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.MutableSharedFlow
import world.gregs.voidps.network.Decoder
import world.gregs.voidps.network.Instruction
import world.gregs.voidps.network.instruct.Command
import world.gregs.voidps.network.readString

class ConsoleCommandDecoder : Decoder(BYTE) {

    override suspend fun decode(instructions: MutableSharedFlow<Instruction>, packet: ByteReadPacket) {
        packet.readUByte()
        packet.readUByte()
        val command = packet.readString()
        val parts = command.split(" ")
        val prefix = parts[0]
        instructions.emit(Command(prefix, command.removePrefix("$prefix ")))
    }

}