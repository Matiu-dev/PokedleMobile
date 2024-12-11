package pl.matiu.pokebdemobile.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.matiu.pokebdemobile.domain.entity.PokemonModel

@Dao
interface PokemonDao {

    @Query("Select * FROM PokemonModel WHERE name = :name")
    fun getPokemonByName(name: String): PokemonModel?

    @Insert
    fun addPokemon(pokemonModel: PokemonModel)
}