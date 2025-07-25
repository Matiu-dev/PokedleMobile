package pl.matiu.pokebdemobile.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    var name: String? = "",
    var typeList: List<String>? = mutableListOf(""),
    var color: String? = "",
    var environment: String? = "",
//    var evolutionStage: Int? = -1,
    var averageHeight: Int? = -1,
    var averageWeight: Int? = -1,
    var isLegendary: Boolean = false,
    var isMythical: Boolean = false,
    var isFromEvolution: Boolean = false
)