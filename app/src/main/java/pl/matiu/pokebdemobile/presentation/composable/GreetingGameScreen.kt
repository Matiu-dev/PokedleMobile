package pl.matiu.pokebdemobile.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class GreetingGameScreen(val modifier: Modifier):  Screen{
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        Column(modifier = modifier) {
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                onClick = {
                    navigator.push(GameScreen(modifier))
                }
            ) {
                Text("Rozpocznij szukanie")
            }
        }
    }
}