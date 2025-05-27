package pl.matiu.pokebdemobile.presentation.viewmodel

import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.presentation.service.LoadingState


data class GameScreenState(
    val todayPokemonModel: PokemonModel? = PokemonModel(),
    val pokemonModel: List<PokemonModel> = emptyList(),
    val isLoading: LoadingState = LoadingState.BEFORE_LOADING
)