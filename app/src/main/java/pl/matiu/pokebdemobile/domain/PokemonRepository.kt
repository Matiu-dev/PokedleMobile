package pl.matiu.pokebdemobile.domain

interface PokemonRepository {
    suspend fun getPokemonData(pokemonName: String): PokemonModel
}