package org.redrune.network.codec.update.decode.message

import org.redrune.core.network.message.Message

/**
 * @author Tyluur <contact@kiaira.tech>
 * @since February 18, 2020
 */
data class UpdateConnectionMessage(val value: Int) : Message