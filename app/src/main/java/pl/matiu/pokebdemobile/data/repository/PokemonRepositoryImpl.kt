package pl.matiu.pokebdemobile.data.repository

import android.util.Log
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.api.PokeApi
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.data.dto.PokemonSpeciesDto
import pl.matiu.pokebdemobile.data.mapper.toPokemonModel
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonRepository
import pl.matiu.pokebdemobile.domain.database.PokemonDao
import pl.matiu.pokebdemobile.domain.database.PokemonShotsDao

class PokemonRepositoryImpl: PokemonRepository {

    private val pokeApi: PokeApi by inject(PokeApi::class.java)
    private val pokemonDao: PokemonDao by inject(PokemonDao::class.java)
    private val pokemonShotsDao: PokemonShotsDao by inject(PokemonShotsDao::class.java)

    suspend fun getPokemonByName(name: String): PokemonModel {
        if(pokemonDao.getPokemonByName(name) == null) {
            Log.d("PokemonModel", "Downloading pokemon from API: $name")
            addPokemonToLocalStorage(getPokemonData(name))
        }

        return pokemonDao.getPokemonByName(name)!!
    }

    private suspend fun getPokemonData(pokemonName: String): PokemonModel {

        val gson = Gson()
        val pokemonDataResponse = PokemonModel(id = 0)

        val pokemonSpeciesDto = gson.fromJson(
            pokeApi.getPokemonSpeciesDataAsString(pokemonName = pokemonName),
            PokemonSpeciesDto::class.java)
        pokemonSpeciesDto.toPokemonModel(pokemonDataResponse)

        val pokemonDto = gson.fromJson(
            pokeApi.getPokemonDataAsString(pokemonName = pokemonName),
            PokemonDto::class.java)
        pokemonDto.toPokemonModel(pokemonDataResponse = pokemonDataResponse)

        return pokemonDataResponse
    }

     private fun addPokemonToLocalStorage(pokemonModel: PokemonModel) {
        pokemonDao.addPokemon(pokemonModel)
    }
}