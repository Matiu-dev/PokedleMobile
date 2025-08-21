package pl.matiu.pokebdemobile.data.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.http.path
import pl.matiu.pokebdemobile.domain.entity.PokemonModel

class PokeApi {
    suspend fun getPokemonSpeciesDataAsString(pokemonName: String): String {
        return HttpClient(CIO).get("https://pokeapi.co/api/v2/pokemon-species/${pokemonName}").body()
    }

    suspend fun getPokemonDataAsString(pokemonName: String): String {
        return HttpClient(CIO).get("https://pokeapi.co/api/v2/pokemon/${pokemonName}").body()
    }

    suspend fun getPokemonModelFromInternalServer(pokemonName: String): String {
        return try {
            HttpClient(CIO).get {
                url {
                    host = "matiu-dev.site"

                    protocol = URLProtocol.HTTPS
                    path("saveAllPokemonsToDatabase")
                    parameters.append("pokemonName", pokemonName)
                }
            }.body()
        } catch (e: Exception) {
            Log.d("HTTP", "Błąd: ${e.message}", e)
            "ERROR: ${e.message}"
        }
    }
}
