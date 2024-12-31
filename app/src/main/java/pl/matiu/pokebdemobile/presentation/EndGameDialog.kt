package pl.matiu.pokebdemobile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import pl.matiu.pokebdemobile.ui.theme.endGameDialogBackground
import pl.matiu.pokebdemobile.ui.theme.endGameDialogColorText

@Composable
fun EndGameDialog(numberOfShots: Int, modifier: Modifier) {

    val navigator = LocalNavigator.currentOrThrow

    AlertDialog(
        containerColor = endGameDialogBackground,
        icon = {
            Icon(Icons.Default.CheckCircle, contentDescription = "Example Icon", tint = endGameDialogColorText)
        },
        title = {
            Text(text = "Gratulacje", color = endGameDialogColorText)
        },
        text = {
            Text(text = "Trafiłeś pokemona",  color = endGameDialogColorText,
                modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally))
        },
        onDismissRequest = {
//            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    navigator.push(ResultGameScreen(numberOfShots = numberOfShots, modifier = modifier))
                }
            ) {
                Text("Potwierdź", color = endGameDialogColorText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally))
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