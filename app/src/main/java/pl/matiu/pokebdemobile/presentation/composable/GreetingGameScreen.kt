package pl.matiu.pokebdemobile.presentation.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.domain.TemporaryDatabase
import pl.matiu.pokebdemobile.domain.pokemonNames
import pl.matiu.pokebdemobile.presentation.PokemonViewModel
import kotlin.random.Random

data class GreetingGameScreen(val modifier: Modifier):  Screen{
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val pokemonViewModel: PokemonViewModel = remember {
            PokemonViewModel()
        }

        val isLoading = pokemonViewModel.isLoading.collectAsState()

        when(isLoading.value) {
            LoadingState.BEFORE_LOADING -> {
                StartGameScreen(pokemonViewModel = pokemonViewModel)
            }

            LoadingState.LOADING -> {
                LoadingScreen()
            }

            LoadingState.AFTER_LOADING -> {
                navigator.push(GameScreen(modifier, navigator = navigator))
            }
            LoadingState.ERROR_LOADING -> {
                Log.d("PokemonModel", "error_loading")
            }
        }
    }

    @Composable
    fun StartGameScreen(pokemonViewModel: PokemonViewModel) {

        val scope = rememberCoroutineScope()

        Column(modifier = modifier) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    scope.launch {
                        pokemonViewModel.choosePokemonToGuess().join()
                    }
                }
            ) {
                Text("Rozpocznij szukanie")
            }
        }
    }

    @Composable
    fun LoadingScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}