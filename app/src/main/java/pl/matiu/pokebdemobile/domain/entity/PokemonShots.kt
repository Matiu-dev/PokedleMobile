package pl.matiu.pokebdemobile.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonShots(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String? = "",
)
