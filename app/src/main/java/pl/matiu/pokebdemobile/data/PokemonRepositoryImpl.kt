package pl.matiu.pokebdemobile.data

import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonRepository

class PokemonRepositoryImpl: PokemonRepository {
    override suspend fun getPokemonData(pokemonName: String): PokemonModel {
        return PokeApi().getPokemonData(pokemonName = pokemonName).toPokemonModel()
    }

}