package pl.matiu.pokebdemobile.data.dto

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesDto(
    @SerializedName("color")
    val color: Color,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    @SerializedName("habitat")
    val habitat: Habitat,
    @SerializedName("evolves_from_species")
    val evolveFromSpecies: EvolveFromSpecies? = null
)