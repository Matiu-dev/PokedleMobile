package pl.matiu.pokebdemobile.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import pl.matiu.pokebdemobile.R
import pl.matiu.pokebdemobile.presentation.viewmodel.GreetingPokemonViewModel
import pl.matiu.pokebdemobile.presentation.service.LoadingState
import pl.matiu.pokebdemobile.ui.theme.mainScreenBackground
import pl.matiu.pokebdemobile.ui.theme.mainScreenButtonBackground
import pl.matiu.pokebdemobile.ui.theme.mainScreenButtonText

data class GreetingGameScreen(val modifier: Modifier):  Screen{
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val greetingPokemonViewModel: GreetingPokemonViewModel = remember {
            GreetingPokemonViewModel()
        }

        val isLoading = greetingPokemonViewModel.isLoading.collectAsState()

        when(isLoading.value) {
            LoadingState.BEFORE_LOADING -> {
                StartGameScreen(greetingPokemonViewModel = greetingPokemonViewModel)
            }

            LoadingState.LOADING -> {
                LoadingScreen()
            }

            LoadingState.AFTER_LOADING -> {
                navigator.push(GameScreen(modifier, navigator = navigator))
            }
            LoadingState.ERROR_LOADING -> {
                StartGameScreen(greetingPokemonViewModel = greetingPokemonViewModel, errorLoading = true)
            }
        }
    }

    @Composable
    fun StartGameScreen(greetingPokemonViewModel: GreetingPokemonViewModel, errorLoading: Boolean = false) {

        if(errorLoading) {
            Toast.makeText(LocalContext.current, "Błąd wczytywania pokemona", Toast.LENGTH_SHORT).show()
        }

        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        Column(modifier = modifier.fillMaxSize().background(mainScreenBackground)) {

            Icon(
                painter = painterResource(R.drawable.pokeball),
                contentDescription = "ikona",
                modifier = Modifier.fillMaxWidth().padding(10.dp).weight(1f),
                tint = Color.Unspecified
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    scope.launch {
                        greetingPokemonViewModel.choosePokemonToGuess(context = context).join()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = mainScreenButtonBackground
                )
            ) {
                Text(text = "Znajdź pokemona", color = mainScreenButtonText)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize().background(color = mainScreenBackground),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = mainScreenButtonBackground)
    }
}