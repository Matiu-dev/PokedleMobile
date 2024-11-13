package pl.matiu.pokebdemobile.data

import android.util.Log
import com.google.gson.Gson
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.data.dto.PokemonSpeciesDto
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonRepository

class PokemonRepositoryImpl: PokemonRepository {
    override suspend fun getPokemonData(pokemonName: String): PokemonModel {

        val gson = Gson()
        var pokemonDataResponse: PokemonModel = PokemonModel()

        val responseSpeciesPokemon = PokeApi().getPokemonSpeciesDataAsString(pokemonName = pokemonName)
        val pokemonSpeciesDto = gson.fromJson(responseSpeciesPokemon, PokemonSpeciesDto::class.java)
        pokemonSpeciesDto.toPokemonModel(pokemonDataResponse)

        val responsePokemon = PokeApi().getPokemonDataAsString(pokemonName = pokemonName)
        Log.d("response", responsePokemon)
        val pokemonDto = gson.fromJson(responsePokemon, PokemonDto::class.java)
        pokemonDto.toPokemonModel(pokemonDataResponse = pokemonDataResponse)

        return pokemonDataResponse
    }

//    override suspend fun getPokemonDataAsString(pokemonName: String): String {
//        return PokeApi().getPokemonDataAsString(pokemonName = pokemonName).toString()
//    }

}