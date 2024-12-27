package pl.matiu.pokebdemobile.presentation.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.matiu.pokebdemobile.R
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.presentation.PokemonViewModel
import pl.matiu.pokebdemobile.presentation.composable.service.GameScreenService
import pl.matiu.pokebdemobile.presentation.composable.service.GuessPokemonState
import pl.matiu.pokebdemobile.presentation.composable.service.LoadingState

//TODO na tym ekranie sprawdzanie czy jest internet w trakcie pobierania pokemona z bazy danych
data class GameScreen(val modifier: Modifier, val navigator: Navigator) : Screen {

    @Composable
    override fun Content() {

        val gameScreenService = GameScreenService()

        val context = LocalContext.current
        val state = rememberLazyListState()

        var pokemonName by rememberSaveable { mutableStateOf("") }

        val pokemonViewModel: PokemonViewModel = viewModel()
        val listOfGuessedPokemon = pokemonViewModel.pokemonModel.collectAsState()
        val todayPokemon = pokemonViewModel.todayPokemonModel.collectAsState()
        val isLoading = pokemonViewModel.isLoading.collectAsState()

        var guessPokemonState by remember { mutableStateOf<GuessPokemonState?>(null) }

        LaunchedEffect(Unit) {
            pokemonViewModel.getTodayPokemonModel(context = context)
        }

        val endGame = rememberSaveable { mutableStateOf(false) }
        val showDialog = rememberSaveable { mutableStateOf(false) }

        if (endGame.value) {
            LaunchedEffect(endGame) {
                val del = launch {
                    delay(3500)
                }
                del.join()
                showDialog.value = true
            }
        }

        if (showDialog.value) {
            EndGameDialog(numberOfShots = listOfGuessedPokemon.value.size, modifier = modifier)
        }

        LaunchedEffect(listOfGuessedPokemon) {
            snapshotFlow { state.firstVisibleItemIndex }
                .collect {
                    state.scrollToItem(0)
                }
        }

        when(isLoading.value) {
            LoadingState.AFTER_LOADING -> {
                Column(modifier = modifier) {

                    GuessPokemonEditText(
                        pokemonName = pokemonName,
                        onPokemonNameChange = { pokemonName = it }
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                gameScreenService.checkGuessPokemonState(pokemonName = pokemonName,
                                    todayPokemon = todayPokemon.value,
                                    listOfGuessedPokemon = listOfGuessedPokemon.value,
                                    onGuessPokemonStateChange = {
                                        guessPokemonState = it
                                    }
                                )

                                gameScreenService.handlePokemonGuessState(
                                    context = context,
                                    pokemonName = pokemonName,
                                    pokemonViewModel = pokemonViewModel,
                                    guessPokemonState = guessPokemonState,
                                    onEndGameChange = { endGame.value = it }
                                )
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
                        items(
                            listOfGuessedPokemon.value.reversed(), key = { it.hashCode() }
                        ) { pokemonModel ->
                            if (listOfGuessedPokemon.value.size > 1) {
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
                                pokemonModel = pokemonModel,
                                todayPokemon = todayPokemon.value,
                                gameScreenService = gameScreenService
                            )
                        }
                    }
                }
            }

            LoadingState.BEFORE_LOADING -> LoadingScreen()
            LoadingState.LOADING -> LoadingScreen()
            LoadingState.ERROR_LOADING -> navigator.popAll()
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenerateAnswers(
    pokemonModel: PokemonModel?,
    todayPokemon: PokemonModel?,
    gameScreenService: GameScreenService
) {

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            FlippableCardContainer(
                pokemonModel!!.name.toString(),
                500,
                if (pokemonModel.name == todayPokemon?.name) Color.Green else Color.Red
            )
        }

        var typeList = pokemonModel?.typeList!!


        for (type in typeList) {

            Spacer(modifier = Modifier.padding(5.dp))

            Column {
                FlippableCardContainer(
                    type, 1000,
                    gameScreenService.checkContains(typeList, todayPokemon)
                )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.environment!!, 1500,
                if (pokemonModel.environment == todayPokemon?.environment) Color.Green else Color.Red
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.color!!, 2000,
                if (pokemonModel.color == todayPokemon?.color) Color.Green else Color.Red
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
                if (pokemonModel.averageHeight == todayPokemon?.averageHeight) Color.Green else Color.Red,
                iconUpOrDown = if (pokemonModel.averageHeight!! > todayPokemon?.averageHeight!!) R.drawable.arrowdown else R.drawable.arrowup
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column {
            FlippableCardContainer(
                pokemonModel!!.averageWeight.toString(), 3000,
                if (pokemonModel.averageWeight == todayPokemon?.averageWeight) Color.Green else Color.Red,
                iconUpOrDown = if (pokemonModel.averageWeight!! > todayPokemon?.averageWeight!!) R.drawable.arrowdown else R.drawable.arrowup
            )
        }
    }
}

@Composable
fun GuessPokemonEditText(
    pokemonName: String,
    onPokemonNameChange: (String) -> Unit,
) {

    //TODO po zmiane pokemonName sprawdzenie czy jakas nazwa pokemona sie pokrywa
    val pokemonNames = mutableListOf<String>()
    val pattern = Regex("^${pokemonName}")//TODO Missing closing bracket in character class near index 2
    pl.matiu.pokebdemobile.domain.pokemonNames.forEach {
        if(pattern.containsMatchIn(it) && pokemonName.isNotEmpty()) {
            pokemonNames.add(it)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "wpisz nazwe", modifier = Modifier
                    .padding(end = 5.dp)
            )
        }


        Column(modifier = Modifier.weight(2f)) {

            Row {
                TextField(
                    value = pokemonName,
                    onValueChange = {
                        onPokemonNameChange(it)
                    },
                )
            }

            Row {
                LazyColumn {
                    items(pokemonNames) {
                        if (pokemonName != it) {
                            Row(modifier = Modifier.clickable { onPokemonNameChange(it) }) {
                                Text(text = it)
                            }
                        }
                    }
                }
            }
        }
    }
}