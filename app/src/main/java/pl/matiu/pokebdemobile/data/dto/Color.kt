package pl.matiu.pokebdemobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Color(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)