package pl.matiu.pokebdemobile.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.matiu.pokebdemobile.domain.entity.PokemonShots

@Dao
interface PokemonShotsDao {

    @Insert
    fun insertShotPokemon(pokemonShots: PokemonShots)

    @Query("DELETE FROM PokemonShots")
    fun deleteAllData()

    @Query("SElECT * FROM PokemonShots")
    fun getAllPokemonShoots(): List<PokemonShots>
}