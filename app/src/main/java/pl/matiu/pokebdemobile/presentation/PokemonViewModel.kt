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
import pl.matiu.pokebdemobile.data.repository.PokemonShotsRepositoryImpl
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.TemporaryDatabase.Companion.todayPokemon
import pl.matiu.pokebdemobile.domain.pokemonNames
import kotlin.random.Random

class PokemonViewModel: ViewModel() {

    val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)
    val pokemonShotsRepository: PokemonShotsRepositoryImpl by inject(PokemonShotsRepositoryImpl::class.java)

    private var _pokemonModel = MutableStateFlow<List<PokemonModel>>(emptyList())
    val pokemonModel: StateFlow<List<PokemonModel>> = _pokemonModel

    init {
        loadPokemonShots()
    }

    private fun loadPokemonShots() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                pokemonShotsRepository.getAllPokemonShoots().forEach { pokemonShot ->
                    _pokemonModel.value = _pokemonModel.value.plus(pokemonRepository.getPokemonByName(name = pokemonShot.name.toString()))
                }
            }
        }
    }

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val pokemonDeffered = async(Dispatchers.IO) {
                    pokemonRepository.getPokemonByName(name = pokemonName)
                }

                val pokemonResult = pokemonDeffered.await()

                if(pokemonResult.name.toString() == todayPokemon.name.toString()) {
                    pokemonShotsRepository.deleteAllDataAfterWin()
                } else {
                    pokemonShotsRepository.addPokemonShot(pokemonResult)
                }

                _pokemonModel.value = _pokemonModel.value.plus(pokemonResult)


            }
        }
    }

    fun choosePokemonToGuess() {
        viewModelScope.launch {
            val pokemonDeffered = async(Dispatchers.IO) {
//                pokemonRepository.getPokemonByName(name = pokemonNames[Random.nextInt(0, pokemonNames.size)])
                pokemonRepository.getPokemonByName(name = pokemonNames[3])
            }

            todayPokemon = pokemonDeffered.await()
            Log.d("PokemonModel", "Today pokemon: " + todayPokemon.name.toString())
        }
    }
}