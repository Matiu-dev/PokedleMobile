package pl.matiu.pokebdemobile.domain

import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.entity.PokemonShots

interface PokemonShotsRepository {

    fun getAllPokemonShoots(): List<PokemonShots>
    fun addPokemonShot(pokemonModel: PokemonModel)
    fun deleteAllDataAfterWin()
}