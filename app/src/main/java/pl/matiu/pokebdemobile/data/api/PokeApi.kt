package pl.matiu.pokebdemobile.data.api

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*

class PokeApi {
    suspend fun getPokemonSpeciesDataAsString(pokemonName: String): String {
        return HttpClient(CIO).get("https://pokeapi.co/api/v2/pokemon-species/${pokemonName}").body()
    }

    suspend fun getPokemonDataAsString(pokemonName: String): String {
        return HttpClient(CIO).get("https://pokeapi.co/api/v2/pokemon/${pokemonName}").body()
    }
}
