package pl.matiu.pokebdemobile.presentation.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun EndGameDialog(numberOfShots: Int, modifier: Modifier) {

    val navigator = LocalNavigator.currentOrThrow

    AlertDialog(
        icon = {
            Icon(Icons.Default.CheckCircle, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "Gratulacje")
        },
        text = {
            Text(text = "Trafiłeś pokemona")
        },
        onDismissRequest = {
//            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
//                    onConfirmation()
                    navigator.push(ResultGameScreen(numberOfShots = numberOfShots, modifier = modifier))
                }
            ) {
                Text("Potwierdź")
            }
        },
//        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text("Dismiss")
//            }
//        }
    )
}