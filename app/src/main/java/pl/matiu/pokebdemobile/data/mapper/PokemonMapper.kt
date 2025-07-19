package pl.matiu.pokebdemobile.data.mapper

import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.data.dto.PokemonSpeciesDto
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.entity.PokemonShots

fun PokemonSpeciesDto.toPokemonModel(pokemonDataResponse: PokemonModel) {
    pokemonDataResponse.name = name
    pokemonDataResponse.color = color.name
    pokemonDataResponse.environment = habitat.name
    pokemonDataResponse.isLegendary = isLegendary
    pokemonDataResponse.isMythical = isMythical
    pokemonDataResponse.isFromEvolution = evolveFromSpecies != null
}

fun PokemonDto.toPokemonModel(pokemonDataResponse: PokemonModel) {
    var listTypes = mutableListOf<String>()

    for(type in types) {
        listTypes.add(type.type.name)
    }

    pokemonDataResponse.typeList = listTypes
    pokemonDataResponse.averageWeight = weight
    pokemonDataResponse.averageHeight = height
}

fun PokemonModel.toPokemonShots(pokemonModel: PokemonModel): PokemonShots {
    return PokemonShots(
        name = pokemonModel.name,
        id = pokemonModel.id,
    )
}

