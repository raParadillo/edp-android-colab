package com.example.myapplication.data.network.dto

import com.example.myapplication.domain.Message
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.longOrNull
import java.time.Instant

fun MessageDto.toDomain(): Message = Message(
    id = id ?: "",
    sender = sender ?: "Unknown",
    text = text ?: "",
    createdAt = createdAt.toLongTimestamp()
)

private fun kotlinx.serialization.json.JsonElement?.toLongTimestamp(): Long {
    val primitive = this as? JsonPrimitive ?: return 0L
    return if (primitive.isString) {
        try {
            Instant.parse(primitive.content).toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    } else {
        primitive.longOrNull ?: 0L
    }
}

fun List<MessageDto>.toDomain(): List<Message> =
    map { it.toDomain() }
