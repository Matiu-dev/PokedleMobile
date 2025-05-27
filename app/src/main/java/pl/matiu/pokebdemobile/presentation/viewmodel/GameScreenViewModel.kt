package pl.matiu.pokebdemobile.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.data.repository.PokemonShotsRepositoryImpl
import pl.matiu.pokebdemobile.domain.sharedPrefs.TodayPokemonSharedPrefs
import pl.matiu.pokebdemobile.presentation.service.LoadingState

class GameScreenViewModel : ViewModel() {

    private val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)
    private val pokemonShotsRepository: PokemonShotsRepositoryImpl by inject(
        PokemonShotsRepositoryImpl::class.java
    )

    private var _gameScreenState = MutableStateFlow(GameScreenState())
    val gameScreenState = _gameScreenState.asStateFlow()

    init {
        Log.d("GamePokemonModel", "inicjacja load pokemon shots")
        loadPokemonShoots()
    }

    private fun loadPokemonShoots() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                pokemonShotsRepository.getAllPokemonShoots().forEach { pokemonShot ->
                    try {
                        val pokemonModel =
                            pokemonRepository.getPokemonByName(name = pokemonShot.name.toString())
                        if (pokemonModel != null) {
                            _gameScreenState.value = _gameScreenState.value.copy(pokemonModel = _gameScreenState.value.pokemonModel + pokemonModel)
                        }

                    } catch (e: Exception) {
                        Log.d("GamePokemonModel", "aaa")
                    }
                }

            }
        }
    }

    fun getPokemonInfo(pokemonName: String, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {

                    val pokemonDeferred = async(Dispatchers.IO) {
                        pokemonRepository.getPokemonByName(name = pokemonName)
                    }

                    val pokemonResult = pokemonDeferred.await()

                    if (pokemonResult != null) {
                        if (pokemonResult.name.toString() ==
                            TodayPokemonSharedPrefs().getTodayPokemon(context)
                        ) {
                            pokemonShotsRepository.deleteAllDataAfterWin()
                            TodayPokemonSharedPrefs().setTodayPokemon(
                                context = context,
                                todayPokemonName = ""
                            )
                            Log.d("GamePokemonModel", "Clearing databases and today pokemon.")
                        } else {
                            pokemonShotsRepository.addPokemonShot(pokemonResult)
                            Log.d("GamePokemonModel", "Adding new pokemon to shots table.")
                        }

                        _gameScreenState.value = _gameScreenState.value.copy(pokemonModel = _gameScreenState.value.pokemonModel + pokemonResult)
                    }
                } catch (e: Exception) {
                    Log.d("PokemonModel", "exception $e")
                }

            }
        }
    }

    suspend fun getTodayPokemonModel(context: Context) {
        withContext(Dispatchers.IO) {
            _gameScreenState.value = _gameScreenState.value.copy(isLoading = LoadingState.LOADING)
            try {
                val pokemonDeferred = async(Dispatchers.IO) {
                    pokemonRepository.getPokemonByName(
                        TodayPokemonSharedPrefs().getTodayPokemon(context)
                    )
                }

                val pokemonResult = pokemonDeferred.await()

                _gameScreenState.value = _gameScreenState.value.copy(todayPokemonModel = pokemonResult)
                _gameScreenState.value = _gameScreenState.value.copy(isLoading = LoadingState.AFTER_LOADING)
            } catch (e: Exception) {
                Log.d("GamePokemonModel", "Error getTodayPokemon: $e")
                _gameScreenState.value = _gameScreenState.value.copy(isLoading = LoadingState.ERROR_LOADING)
            }
        }
    }
}