package pl.matiu.pokebdemobile.data.repository

import android.util.Log
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.api.PokeApi
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.data.dto.PokemonSpeciesDto
import pl.matiu.pokebdemobile.data.mapper.toPokemonModel
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonRepository
import pl.matiu.pokebdemobile.domain.database.PokemonDao

class PokemonRepositoryImpl: PokemonRepository {

    val pokeApi: PokeApi by inject(PokeApi::class.java)
    val pokemonDao: PokemonDao by inject(PokemonDao::class.java)

    suspend fun getPokemonByName(name: String): PokemonModel {
        if(pokemonDao.getPokemonByName(name) == null) {
            Log.d("PokemonModel", "Pobierania pokemona z api o nazwie $name")
            addPokemon(getPokemonData(name))
            return pokemonDao.getPokemonByName(name)!!
        } else {
            Log.d("PokemonModel", "Pokemon ${name.toUpperCase()} jest już w bazie danych")
            return pokemonDao.getPokemonByName(name)!!
        }
    }

    private suspend fun getPokemonData(pokemonName: String): PokemonModel {

        val gson = Gson()
        var pokemonDataResponse = PokemonModel(id = 0)

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

     private fun addPokemon(pokemonModel: PokemonModel) {
        pokemonDao.addPokemon(pokemonModel)
    }
}