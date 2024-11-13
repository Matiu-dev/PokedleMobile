package pl.matiu.pokebdemobile.data

import android.util.Log
import pl.matiu.pokebdemobile.data.dto.PokemonDto
import pl.matiu.pokebdemobile.domain.PokemonModel

fun PokemonDto.toPokemonModel(): PokemonModel {

//    Log.d("testsssss", abilities[0].ability.name + " " + abilities[0].ability.url)

    return PokemonModel(name = name,
        typeList = listOf("", ""),
        color =  color.name,
        environment = habitat.name,
        evolutionStage = 1,
        averageWeight = 1F,
        averageHeight = 1F,
        isLegendary = isLegendary,
        isMythical = isMythical)
}