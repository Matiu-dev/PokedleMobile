package pl.matiu.pokebdemobile.data.mapper

import android.util.Log
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.data.dto.PokemonSpeciesDto
import pl.matiu.pokebdemobile.domain.PokemonModel

fun PokemonSpeciesDto.toPokemonModel(pokemonDataResponse: PokemonModel) {
    pokemonDataResponse.name = name
    pokemonDataResponse.color = color.name
    pokemonDataResponse.environment = habitat.name
    pokemonDataResponse.isLegendary = isLegendary
    pokemonDataResponse.isMythical = isMythical
}

fun PokemonDto.toPokemonModel(pokemonDataResponse: PokemonModel) {
    var listTypes = mutableListOf<String>()

    for(type in types) {
        listTypes.add(type.type.name)
        Log.d("types", type.type.name)
    }

    pokemonDataResponse.typeList = listTypes
    pokemonDataResponse.averageWeight = weight
    pokemonDataResponse.averageHeight = height
}