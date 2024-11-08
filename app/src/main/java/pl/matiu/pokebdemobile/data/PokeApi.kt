package pl.matiu.pokebdemobile.data

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*

class PokeApi {
    suspend fun getPokemonData(pokemonName: String): PokemonDto {
        return HttpClient(CIO).get("https://pokeapi.co/api/v2/pokemon/${pokemonName}").body()
    }
}
