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
//    private var _pokemonModel = MutableStateFlow<PokemonModel?>(null)
    val pokemonModel: StateFlow<List<PokemonModel>> = _pokemonModel

    init {
//        getPokemonInfo(pokemonName = "charmander")
    }

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            _pokemonModel.value = _pokemonModel.value.plus(PokemonRepositoryImpl().getPokemonData(pokemonName = pokemonName))
//            _pokemonModel.value = PokemonRepositoryImpl().getPokemonDataAsString(pokemonName = pokemonName)
        }
    }
}