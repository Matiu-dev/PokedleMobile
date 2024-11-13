package pl.matiu.pokebdemobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.pokebdemobile.data.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.domain.PokemonModel

class PokemonViewModel: ViewModel() {

    private var _pokemonModel = MutableStateFlow<List<PokemonModel>>(emptyList())
    val pokemonModel: StateFlow<List<PokemonModel>> = _pokemonModel

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            _pokemonModel.value = _pokemonModel.value.plus(PokemonRepositoryImpl().getPokemonData(pokemonName = pokemonName))
        }
    }
}