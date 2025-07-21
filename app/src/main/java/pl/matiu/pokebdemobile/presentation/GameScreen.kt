package pl.matiu.pokebdemobile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.matiu.pokebdemobile.R
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.presentation.viewmodel.GameScreenViewModel
import pl.matiu.pokebdemobile.presentation.service.GameScreenService
import pl.matiu.pokebdemobile.presentation.service.GuessPokemonState
import pl.matiu.pokebdemobile.presentation.service.LoadingState
import pl.matiu.pokebdemobile.ui.theme.dividerColor
import pl.matiu.pokebdemobile.ui.theme.editTextBackground
import pl.matiu.pokebdemobile.ui.theme.editTextText
import pl.matiu.pokebdemobile.ui.theme.mainScreenBackground
import pl.matiu.pokebdemobile.ui.theme.mainScreenButtonBackground
import pl.matiu.pokebdemobile.ui.theme.mainScreenButtonText

data class GameScreen(val modifier: Modifier, val navigator: Navigator) : Screen {

    @Composable
    override fun Content() {

        val gameScreenService = GameScreenService()

        val context = LocalContext.current
        val state = rememberLazyListState()

        var pokemonName by rememberSaveable { mutableStateOf("") }

        val gameScreenViewModel: GameScreenViewModel = viewModel()
        val gameScreenState = gameScreenViewModel.gameScreenState.collectAsState()

        var guessPokemonState by remember { mutableStateOf<GuessPokemonState?>(null) }

        LaunchedEffect(Unit) {
            gameScreenViewModel.getTodayPokemonModel(context = context)
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
            EndGameDialog(numberOfShots = gameScreenState.value.pokemonModel.size, modifier = modifier)
        }

        LaunchedEffect(gameScreenState.value.pokemonModel) {
            snapshotFlow { state.firstVisibleItemIndex }
                .collect {
                    state.scrollToItem(0)
                }
        }

        when (gameScreenState.value.isLoading) {
            LoadingState.AFTER_LOADING -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = mainScreenBackground)
                ) {

                    GuessPokemonEditText(
                        pokemonName = pokemonName,
                        onPokemonNameChange = { pokemonName = it }
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                gameScreenService.checkGuessPokemonState(context = context,
                                    pokemonName = pokemonName,
                                    todayPokemon = gameScreenState.value.todayPokemonModel,
                                    listOfGuessedPokemon = gameScreenState.value.pokemonModel,
                                    onGuessPokemonStateChange = {
                                        guessPokemonState = it
                                    }
                                )

                                gameScreenService.handlePokemonGuessState(
                                    context = context,
                                    pokemonName = pokemonName,
                                    greetingPokemonViewModel = gameScreenViewModel,
                                    guessPokemonState = guessPokemonState,
                                    onEndGameChange = { endGame.value = it }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = mainScreenButtonBackground
                            )
                        ) {
                            Text(text = "Sprawdź", color = mainScreenButtonText)
                        }
                    }

                    LazyColumn(state = state, modifier = Modifier.padding(5.dp)) {
                        items(
                            gameScreenState.value.pokemonModel.reversed(), key = { it.hashCode() }
                        ) { pokemonModel ->
                            if (gameScreenState.value.pokemonModel.size > 1) {
                                HorizontalDivider(
                                    thickness = 2.dp,
                                    color = dividerColor,
                                    modifier = Modifier.padding(
                                        horizontal = 15.dp,
                                        vertical = 15.dp
                                    )
                                )
                            }

                            GenerateAnswers(
                                pokemonModel = pokemonModel,
                                todayPokemon = gameScreenState.value.todayPokemonModel,
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
fun Header() {
    LazyRow(
        modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        item {

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Name"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Type 1"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Type 2"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Name"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Name"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Name"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Name"
                )

                Text(
                    modifier = Modifier.height(100.dp).width(100.dp),
                    text = "Name"
                )

        }
    }
}

@Composable
fun GenerateAnswers(
    pokemonModel: PokemonModel?,
    todayPokemon: PokemonModel?,
    gameScreenService: GameScreenService
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    text = "Name"
                )

                FlippableCardContainer(
                    pokemonModel!!.name.toString(),
                    500,
                    if (pokemonModel.name == todayPokemon?.name) Color.Green else Color.Red
                )
            }

            var typeList = pokemonModel?.typeList!!


            for (type in typeList) {

                Spacer(modifier = Modifier.padding(5.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.White,
                        text = "Type"
                    )

                    FlippableCardContainer(
                        type, 1000,
                        gameScreenService.checkContains(typeList, todayPokemon)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    text = "Habitat"
                )

                FlippableCardContainer(
                    pokemonModel.environment!!, 1500,
                    if (pokemonModel.environment == todayPokemon?.environment) Color.Green else Color.Red
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    text = "Color"
                )

                FlippableCardContainer(
                    pokemonModel.color!!, 2000,
                    if (pokemonModel.color == todayPokemon?.color) Color.Green else Color.Red
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    text = "Evolved"
                )

                FlippableCardContainer(
                    if(pokemonModel.isFromEvolution) "True" else "False" , 2000,
                    if (pokemonModel.isFromEvolution == todayPokemon?.isFromEvolution) Color.Green else Color.Red
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    text = "Height"
                )

                FlippableCardContainer(
                    pokemonModel.averageHeight.toString(), 2500,
                    if (pokemonModel.averageHeight == todayPokemon?.averageHeight) Color.Green else Color.Red,
                    iconUpOrDown = if (pokemonModel.averageHeight!! > todayPokemon?.averageHeight!!) R.drawable.arrowdown else R.drawable.arrowup
                )
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    text = "Width"
                )

                FlippableCardContainer(
                    pokemonModel!!.averageWeight.toString(), 3000,
                    if (pokemonModel.averageWeight == todayPokemon?.averageWeight) Color.Green else Color.Red,
                    iconUpOrDown = if (pokemonModel.averageWeight!! > todayPokemon?.averageWeight!!) R.drawable.arrowdown else R.drawable.arrowup
                )
            }
        }
    }
}

@Composable
fun GuessPokemonEditText(
    pokemonName: String,
    onPokemonNameChange: (String) -> Unit,
) {

    val density = LocalDensity.current

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    val pokemonNames = mutableListOf<String>()
    val pattern = Regex("^${pokemonName}")
    pl.matiu.pokebdemobile.domain.pokemonNames.forEach {
        if (pattern.containsMatchIn(it) && pokemonName.isNotEmpty() && it != pokemonName) {
            pokemonNames.add(it)
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 10.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = pokemonName,
                    onValueChange = {
                        onPokemonNameChange(it)
                    },
                    placeholder = {
                        Text(
                            text = "Wpisz nazwę pokemona",
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    columnHeightDp = with(density) {
                                        coordinates.size.height.toDp()
                                    }
                                }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = editTextBackground,
                        unfocusedContainerColor = editTextBackground,
                        focusedTextColor = editTextText,
                        unfocusedTextColor = editTextText,
                        cursorColor = editTextText,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent
                    )
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(
                        if(pokemonNames.size < 5) {
                            columnHeightDp * pokemonNames.size
                        } else {
                            columnHeightDp * 5
                        }
                    )
            ) {
                items(pokemonNames) {
                    Row(modifier = Modifier.clickable { onPokemonNameChange(it) }) {
                        Text(text = it, color = editTextText)
                    }
                }
            }
        }
    }
}