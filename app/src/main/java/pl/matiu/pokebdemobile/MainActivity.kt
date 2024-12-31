package pl.matiu.pokebdemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import pl.matiu.pokebdemobile.presentation.GreetingGameScreen
import pl.matiu.pokebdemobile.ui.theme.PokebdeMobileTheme
import pl.matiu.pokebdemobile.ui.theme.topBarBackground
import pl.matiu.pokebdemobile.ui.theme.topBarTitle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PokebdeMobileTheme {
                Greeting()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = topBarBackground,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Pokedle Mobile", color = topBarTitle)
                },
//                navigationIcon = {
//                    IconButton(onClick = { /* do something */ }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Localized description"
//                        )
//                    }
//                }
            )
        },
//        bottomBar = {
//            BottomAppBar(
//                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                contentColor = MaterialTheme.colorScheme.primary,
//            ) {
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    text = "Bottom app bar",
//                )
//            }
//        },
    ) { innerPadding ->

        Navigator(GreetingGameScreen(modifier = Modifier.padding(innerPadding))) { navigator ->
            SlideTransition(navigator)
        }
    }
}

