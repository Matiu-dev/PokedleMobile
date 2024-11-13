package pl.matiu.pokebdemobile.data

import android.util.Log
import com.google.gson.Gson
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonRepository

class PokemonRepositoryImpl: PokemonRepository {
    override suspend fun getPokemonData(pokemonName: String): PokemonModel {

        val response = PokeApi().getPokemonDataAsString(pokemonName = pokemonName)
        val gson = Gson()
        val pokemonDto = gson.fromJson(response, PokemonDto::class.java)
        return pokemonDto.toPokemonModel()
    }

//    override suspend fun getPokemonDataAsString(pokemonName: String): String {
//        return PokeApi().getPokemonDataAsString(pokemonName = pokemonName).toString()
//    }

}