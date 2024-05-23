package me.nexryai.transit.entities

data class TransitParams(
    val from: String,
    val to: String,
    val date: String,
    val time: String,
)