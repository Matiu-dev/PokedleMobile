package pl.matiu.pokebdemobile.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.data.repository.PokemonShotsRepositoryImpl
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.pokemonNames
import pl.matiu.pokebdemobile.domain.sharedPrefs.TodayPokemonSharedPrefs
import pl.matiu.pokebdemobile.presentation.composable.service.LoadingState
import kotlin.random.Random

class PokemonViewModel : ViewModel() {

    private val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)
    private val pokemonShotsRepository: PokemonShotsRepositoryImpl by inject(
        PokemonShotsRepositoryImpl::class.java
    )

    private var _todayPokemonModel = MutableStateFlow<PokemonModel?>(null)
    val todayPokemonModel: StateFlow<PokemonModel?> = _todayPokemonModel.asStateFlow()

    private var _pokemonModel = MutableStateFlow<List<PokemonModel>>(emptyList())
    val pokemonModel: StateFlow<List<PokemonModel>> = _pokemonModel.asStateFlow()

    private var _isLoading = MutableStateFlow(LoadingState.BEFORE_LOADING)
    val isLoading = _isLoading.asStateFlow()

    init {
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
                            _pokemonModel.value =
                                _pokemonModel.value.plus(pokemonModel)
                        }

                    } catch (e: Exception) {
                        Log.d("PokemonModel", "aaa")
                    }
                }

            }
        }
    }


    fun getPokemonInfo(pokemonName: String, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

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
                        Log.d("PokemonModel", "Clearing databases and today pokemon.")
                    } else {
                        pokemonShotsRepository.addPokemonShot(pokemonResult)
                        Log.d("PokemonModel", "Adding new pokemon to shots table.")
                    }

                    _pokemonModel.value = _pokemonModel.value.plus(pokemonResult)
                }


            }
        }
    }

    fun choosePokemonToGuess(context: Context) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _isLoading.value = LoadingState.LOADING

            if (TodayPokemonSharedPrefs().getTodayPokemon(context = context) == "") {
                val todayPokemon = pokemonRepository.getPokemonByName(
                    name = pokemonNames[Random.nextInt(
                        0,
                        pokemonNames.size - 1
                    )]
                )

                if (todayPokemon != null) {
                    todayPokemon.name?.let {
                        TodayPokemonSharedPrefs().setTodayPokemon(
                            context = context,
                            todayPokemonName = todayPokemon.name.toString()
                        )
                    }

                    Log.d("PokemonModel", "Today pokemon: " + todayPokemon.name.toString())
                    _isLoading.value = LoadingState.AFTER_LOADING
                } else {
                    _isLoading.value = LoadingState.ERROR_LOADING
                }

            } else {
                Log.d(
                    "PokemonModel",
                    "Today pokemon was selected: " + TodayPokemonSharedPrefs().getTodayPokemon(
                        context = context
                    )
                )
                _isLoading.value = LoadingState.AFTER_LOADING
            }
        }
    }

    suspend fun getTodayPokemonModel(context: Context) {
        withContext(Dispatchers.IO) {
            _isLoading.value = LoadingState.LOADING
            try {
                val pokemonDeffered = async(Dispatchers.IO) {
                    pokemonRepository.getPokemonByName(
                        TodayPokemonSharedPrefs().getTodayPokemon(context)
                    )
                }

                val pokemonResult = pokemonDeffered.await()

                _todayPokemonModel.value = pokemonResult
                _isLoading.value = LoadingState.AFTER_LOADING
            } catch (e: Exception) {
                Log.d("PokemonModel", "Error getTodayPokemon: $e")
                _isLoading.value = LoadingState.ERROR_LOADING
            }
        }
    }
}