package pl.matiu.pokebdemobile.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class EvolveFromSpecies(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
