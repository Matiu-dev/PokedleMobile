package pl.matiu.pokebdemobile

import android.util.Log
import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.abs

data class GameScreen(val modifier: Modifier) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val listOfGuessedPokemon = remember { mutableStateListOf<String>() }

        var pokemonName by rememberSaveable { mutableStateOf("") }
//        var generateNewValue by remember { mutableStateOf(false) }


        Column(modifier = modifier) {

            GuessPokemonEditText(pokemonName = pokemonName,
                onPokemonNameChange = { pokemonName = it })
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        listOfGuessedPokemon.add(pokemonName)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Sprawdź")
                }
            }

            LazyColumn {
                items(
                    listOfGuessedPokemon
                ) { pokemonName ->
//                    Log.d("genrate new v", generateNewValue.toString())
                    GenerateAnswers(
//                        generateNewValue = generateNewValue,
//                        onGenerateNewValue = { generateNewValue = it },
                        pokemonName = pokemonName
                    )
                }
            }

        }
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenerateAnswers(
//    generateNewValue: Boolean,
//    onGenerateNewValue: (Boolean) -> Unit,
    pokemonName: String
) {

    var launchSecond = remember { mutableStateOf(false) }
    var launchThird = remember { mutableStateOf(false) }
    var launchFourth = remember { mutableStateOf(false) }
    var launchFifeth = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        launchSecond.value = true
        delay(1500)
        launchThird.value = true
        delay(1500)
        launchFourth.value = true
        delay(1500)
        launchFifeth.value = true
        delay(1500)

//        onGenerateNewValue(false)
    }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            FlippableCardContainer(pokemonName)
        }

        if (launchSecond.value) {
            Column {
                FlippableCardContainer(pokemonName)
            }
        }

        if (launchThird.value) {
            Column {
                FlippableCardContainer(pokemonName)
            }
        }

        if (launchFourth.value) {
            Column {
                FlippableCardContainer(pokemonName)
            }
        }

        if (launchFifeth.value) {
            Column {
                FlippableCardContainer(pokemonName)
            }
        }

    }

    HorizontalDivider(
        thickness = 2.dp,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
}

@Composable
fun GuessPokemonEditText(
    pokemonName: String,
    onPokemonNameChange: (String) -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
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

@Composable
fun TextBoxItem() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row { }
        Row { }
    }
}