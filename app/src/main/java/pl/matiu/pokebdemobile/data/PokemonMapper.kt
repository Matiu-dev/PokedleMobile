package pl.matiu.pokebdemobile.data

import pl.matiu.pokebdemobile.domain.PokemonModel

fun PokemonDto.toPokemonModel(): PokemonModel {
    return PokemonModel("", listOf("", ""), "", "", 1, 1F, 1F)
}