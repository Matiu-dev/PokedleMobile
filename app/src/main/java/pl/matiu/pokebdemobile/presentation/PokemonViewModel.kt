package pl.matiu.pokebdemobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.pokebdemobile.data.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.domain.PokemonModel

class PokemonViewModel: ViewModel() {

    private var _pokemonModel = MutableStateFlow<List<PokemonModel>?>(null)
    val pokemonModel = _pokemonModel.asStateFlow()

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            _pokemonModel.value = listOf( PokemonRepositoryImpl().getPokemonData(pokemonName = pokemonName))
        }
    }
}