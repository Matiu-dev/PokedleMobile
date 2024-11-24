package pl.matiu.pokebdemobile.presentation.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
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

        val pokemonViewModel: PokemonViewModel = viewModel()
        var listOfGuessedPokemon = pokemonViewModel.pokemonModel.collectAsState()

        Column(modifier = modifier) {
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    //choose pokemon
                    pokemonViewModel.choosePokemonToGuess()

                    navigator.push(GameScreen(modifier))
//                    navigator.push(ResultGameScreen(modifier = modifier, numberOfShots = 1))
                }
            ) {
                Text("Rozpocznij szukanie")
            }
        }
    }
}