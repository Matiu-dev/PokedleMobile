package pl.matiu.pokebdemobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto (
    @SerialName("types")
    val types: List<Types>,
    @SerialName("weight")
    val weight: Int,
    @SerialName("height")
    val height: Int
)