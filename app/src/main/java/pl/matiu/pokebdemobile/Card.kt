package pl.matiu.pokebdemobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private const val BankCardAspectRatio = 1.0f

@Composable
internal fun Card(
    rotationAngle: Float,
    modifier: Modifier = Modifier,
    pokemonName: String
) {
    val sideModifier =
        modifier
            .height(100.dp)
            .aspectRatio(BankCardAspectRatio)
            .graphicsLayer {
                rotationY = rotationAngle
                cameraDistance = 5.dp.toPx()
            }
            .clip(shape = RoundedCornerShape(20.dp))

    Box {
        Box(
            modifier = sideModifier
                .graphicsLayer {
                    alpha = if (rotationAngle in 90f..180f) 0f else 1f
                }
                .background(Color.Transparent),
        )

        Box(
            modifier = sideModifier
                .graphicsLayer {
                    alpha = if (rotationAngle in 90f..180f) 1f else 0f
                    rotationY = 180f
                }
                .background(Color.Green),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = pokemonName, color = Color.White, textAlign = TextAlign.Center)
            }
        }
    }
}