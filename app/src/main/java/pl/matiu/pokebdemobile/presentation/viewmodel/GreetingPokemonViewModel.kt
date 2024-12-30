package pl.matiu.pokebdemobile.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.domain.pokemonNames
import pl.matiu.pokebdemobile.domain.sharedPrefs.TodayPokemonSharedPrefs
import pl.matiu.pokebdemobile.presentation.service.LoadingState
import kotlin.random.Random

class GreetingPokemonViewModel : ViewModel() {

    private val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)

    private var _isLoading = MutableStateFlow(LoadingState.BEFORE_LOADING)
    val isLoading = _isLoading.asStateFlow()

    init {
        Log.d("GreetingPokemonModel", "inicjacja")
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
}