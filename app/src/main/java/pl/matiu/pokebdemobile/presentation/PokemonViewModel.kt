package pl.matiu.pokebdemobile.presentation

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
import pl.matiu.pokebdemobile.presentation.composable.LoadingState
import kotlin.random.Random

class PokemonViewModel : ViewModel() {

    private val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)
    private val pokemonShotsRepository: PokemonShotsRepositoryImpl by inject(
        PokemonShotsRepositoryImpl::class.java
    )

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
                    _pokemonModel.value =
                        _pokemonModel.value.plus(pokemonRepository.getPokemonByName(name = pokemonShot.name.toString()))
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

                if (pokemonResult.name.toString() ==
                    TodayPokemonSharedPrefs().getTodayPokemon(context)) {
                    pokemonShotsRepository.deleteAllDataAfterWin()
                    TodayPokemonSharedPrefs().setTodayPokemon(context = context, todayPokemonName = "")
                    Log.d("PokemonModel", "clearing data")
                } else {
                    pokemonShotsRepository.addPokemonShot(pokemonResult)
                    Log.d("PokemonModel", "adding new pokemon to shots")
                }

                _pokemonModel.value = _pokemonModel.value.plus(pokemonResult)


            }
        }
    }

    fun choosePokemonToGuess(context: Context) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _isLoading.value = LoadingState.LOADING
            try {

                if(TodayPokemonSharedPrefs().getTodayPokemon(context = context) == "") {
                    val todayPokemon = pokemonRepository.getPokemonByName(
                        name = pokemonNames[Random.nextInt(
                            0,
                            pokemonNames.size - 1
                        )]
                    )

                    todayPokemon.name?.let {
                        TodayPokemonSharedPrefs().setTodayPokemon(context = context, todayPokemonName = todayPokemon.name.toString())
                    }

                    Log.d("PokemonModel", "Today pokemon: " + todayPokemon.name.toString())
                } else {
                    Log.d("PokemonModel", "Today pokemon was selected: " + TodayPokemonSharedPrefs().getTodayPokemon(context = context))
                }


                _isLoading.value = LoadingState.AFTER_LOADING
            } catch (e: Exception) {
                _isLoading.value = LoadingState.BEFORE_LOADING
                Log.d("PokemonModel", "Error: $e")
            }
        }
    }

    suspend fun getTodayPokemonModel(context: Context): PokemonModel? {
        return withContext(Dispatchers.IO) {
            pokemonRepository.getPokemonByName(TodayPokemonSharedPrefs().getTodayPokemon(context))
        }
    }
}