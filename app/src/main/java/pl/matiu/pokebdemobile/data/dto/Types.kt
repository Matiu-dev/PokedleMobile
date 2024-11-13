package pl.matiu.pokebdemobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Types(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: Type
)