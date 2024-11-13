package pl.matiu.pokebdemobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    @SerialName("color")
    val color: Color,
    @SerialName("name")
    val name: String,
    @SerialName("is_legendary")
    val isLegendary: Boolean,
    @SerialName("is_mythical")
    val isMythical: Boolean,
    @SerialName("habitat")
    val habitat: Habitat
)