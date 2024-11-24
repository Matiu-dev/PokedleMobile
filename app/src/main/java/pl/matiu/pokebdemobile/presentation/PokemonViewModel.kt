package pl.matiu.pokebdemobile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.TemporaryDatabase
import pl.matiu.pokebdemobile.domain.TemporaryDatabase.Companion.todayPokemon
import pl.matiu.pokebdemobile.domain.pokemonNames
import kotlin.random.Random

class PokemonViewModel: ViewModel() {

    val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)

    private var _pokemonModel = MutableStateFlow<List<PokemonModel>>(emptyList())
    val pokemonModel: StateFlow<List<PokemonModel>> = _pokemonModel

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val pokemonDeffered = async(Dispatchers.IO) {
                    pokemonRepository.getPokemonByName(name = pokemonName)
                }

                val pokemon = pokemonDeffered.await()

                _pokemonModel.value = _pokemonModel.value.plus(pokemon)
            }

        }
    }

    fun choosePokemonToGuess() {
        viewModelScope.launch {
            val pokemonDeffered = async(Dispatchers.IO) {
                pokemonRepository.getPokemonByName(name = pokemonNames[Random.nextInt(0, pokemonNames.size)])
            }

            val pokemon = pokemonDeffered.await()

            if(pokemon != null) {
                todayPokemon = pokemon
                Log.d("PokemonModel", "Wylosowany pokemon to: " +pokemon.name.toString())
            }else {
                Log.e("Error", "Pokemon not found!")
            }
        }
    }

}