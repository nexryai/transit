package me.nexryai.transit.entities

import java.time.LocalDateTime

enum class TimeMode {
    DEPARTURE,
    ARRIVAL,
    IGNORE
}

data class TransitParams(
    val from: String,
    val to: String,
    val time: LocalDateTime,
    val timeMode: TimeMode
)