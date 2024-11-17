package pl.matiu.pokebdemobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.domain.PokemonModel

class PokemonViewModel: ViewModel() {

    val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)

    private var _pokemonModel = MutableStateFlow<List<PokemonModel>>(emptyList())
    val pokemonModel: StateFlow<List<PokemonModel>> = _pokemonModel

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            _pokemonModel.value = _pokemonModel.value.plus(pokemonRepository.getPokemonData(pokemonName = pokemonName))
        }
    }
}