package me.nexryai.transit.entities

import kotlinx.serialization.Serializable


@Serializable
data class Train(
    val displayInfo: String,
    val destination: String,
    val numOfStops: Int,
    val style: String,
)

@Serializable
data class Transfer(
    val stationName: String,
    val arrive: String,
    val depart: String,
    val ridingPosition: String,
    var train: Train?,
)

@Serializable
data class TransitInfo(
    val price: String,
    val numOfTransfers: Int,
    val transfers: List<Transfer>,
)