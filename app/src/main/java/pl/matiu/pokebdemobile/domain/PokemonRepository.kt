package pl.matiu.pokebdemobile.domain

import pl.matiu.pokebdemobile.domain.entity.PokemonModel

interface PokemonRepository {
    suspend fun getPokemonByName(name: String): PokemonModel?
}