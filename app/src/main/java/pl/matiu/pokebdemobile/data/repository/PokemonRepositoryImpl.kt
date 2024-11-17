package pl.matiu.pokebdemobile.data.repository

import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.api.PokeApi
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.data.dto.PokemonSpeciesDto
import pl.matiu.pokebdemobile.data.mapper.toPokemonModel
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonRepository

class PokemonRepositoryImpl: PokemonRepository {

    val pokeApi: PokeApi by inject(PokeApi::class.java)

    override suspend fun getPokemonData(pokemonName: String): PokemonModel {

        val gson = Gson()
        var pokemonDataResponse = PokemonModel()

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
}