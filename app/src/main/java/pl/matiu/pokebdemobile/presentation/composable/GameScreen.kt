package pl.matiu.pokebdemobile.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.matiu.pokebdemobile.domain.TemporaryDatabase
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.presentation.PokemonViewModel

data class GameScreen(val modifier: Modifier) : Screen {

    @Composable
    override fun Content() {

        val context = LocalContext.current
        val state = rememberLazyListState()

        val navigator = LocalNavigator.currentOrThrow
//        val listOfGuessedPokemon = remember { mutableStateListOf<PokemonModel>() }
        var pokemonName by rememberSaveable { mutableStateOf("") }

        val pokemonViewModel: PokemonViewModel = viewModel()
        var listOfGuessedPokemon = pokemonViewModel.pokemonModel.collectAsState()

        LaunchedEffect(listOfGuessedPokemon) {
            snapshotFlow { state.firstVisibleItemIndex }
                .collect {
                    state.scrollToItem(0)
                }
        }

        Column(modifier = modifier) {

            GuessPokemonEditText(pokemonName = pokemonName,
                onPokemonNameChange = { pokemonName = it })

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
//                        if (!isPokemonExist(pokemonName)
//                        ) {
                        //TODo sprawdzenie czy dany pokemon istnieje

                        if (!isPokemonSelected(listOfGuessedPokemon.value, pokemonName)) {
                            pokemonViewModel.getPokemonInfo(pokemonName = pokemonName)

                            if (pokemonName == TemporaryDatabase.todayPokemon.name) {
                                Toast.makeText(
                                    context,
                                    "Udało Ci się zgadnąć. Dzisiejszy pokemon to ${pokemonName}.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Nie trafiłeś tym razem. Spróbuj ponownie.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Sprawdziłeś już tego pokemona.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }


//                        } else {
//                            Toast.makeText(context, "Nie ma takiego pokemona.", Toast.LENGTH_SHORT)
//                                .show()
//                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .padding(horizontal = 10.dp)
                ) {
                    Text(text = "Sprawdź")
                }
            }

            LazyColumn(state = state, modifier = Modifier.padding(5.dp)) {
                if (listOfGuessedPokemon.value != null) {
                    items(
                        listOfGuessedPokemon.value!!.reversed(), key = { it.hashCode() }
                    ) { pokemonModel ->
                        if (listOfGuessedPokemon.value!!.size > 1) {
                            HorizontalDivider(
                                thickness = 2.dp,
                                color = Color.Black,
                                modifier = Modifier.padding(
                                    horizontal = 15.dp,
                                    vertical = 15.dp
                                )
                            )
                        }

                        GenerateAnswers(
                            pokemonModel = pokemonModel
                        )
                    }
                }

            }

        }
    }
}

fun isPokemonExist(pokemonName: String): Boolean {
    return TemporaryDatabase.pokemonGuessList.asSequence()
        .filter { it.value.name == pokemonName }.firstOrNull() == null
}

fun isPokemonSelected(guessedPokemonList: List<PokemonModel>, pokemonName: String): Boolean {
    return guessedPokemonList.any { it.name == pokemonName }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenerateAnswers(
    pokemonModel: PokemonModel?
) {

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
//        items(1) { item ->
        Column {
            FlippableCardContainer(
                pokemonModel!!.name.toString(),
                500,
                if (pokemonModel.name == TemporaryDatabase.todayPokemon.name) Color.Green else Color.Red
            )
        }

//            Spacer(modifier = Modifier.padding(5.dp))

        var typeList = pokemonModel?.typeList!!


        for (type in typeList) {

            Spacer(modifier = Modifier.padding(5.dp))

            Column {
                FlippableCardContainer(
                    type, 1000,
                    checkContains(typeList)
                )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.environment!!, 1500,
                if (pokemonModel.environment == TemporaryDatabase.todayPokemon.environment) Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.color!!, 2000,
                if (pokemonModel.color == TemporaryDatabase.todayPokemon.color) Color.Green else Color.Red
            )
        }

//            Spacer(modifier = Modifier.padding(5.dp))

//            Column {
//                FlippableCardContainer(
//                    pokemonModel!!.evolutionStage.toString(), 3000,
//                    if (pokemonModel.evolutionStage == TemporaryDatabase.todayPokemon.evolutionStage) Color.Green else Color.Red
//                )
//            }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.averageHeight.toString(), 2500,
                if (pokemonModel.averageHeight == TemporaryDatabase.todayPokemon.averageHeight) Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.averageWeight.toString(), 3000,
                if (pokemonModel.averageWeight == TemporaryDatabase.todayPokemon.averageWeight) Color.Green else Color.Red
            )
        }
//        }

    }
}

@Composable
fun GuessPokemonEditText(
    pokemonName: String,
    onPokemonNameChange: (String) -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "wpisz nazwe", modifier = Modifier
                .padding(end = 5.dp)
                .weight(1f)
        )

        TextField(
            value = pokemonName,
            onValueChange = { onPokemonNameChange(it) },
            modifier = Modifier.weight(2f)
        )
    }
}

fun checkContains(typeList: List<String>): Color {
    var containsAny = false
    var containsAll = true


    for (typeInGuessedPokemon in typeList) {
        if (typeInGuessedPokemon in TemporaryDatabase.todayPokemon.typeList!!) {
            containsAny = true
        } else {
            containsAll = false
        }
    }

    return if (containsAll) {
        Color.Green
    } else {
        if (containsAny) {
            Color.Yellow
        } else {
            Color.Red
        }
    }
}

enum class TypeContains() {
    All, ANY, NONE
}