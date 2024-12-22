package pl.matiu.pokebdemobile.presentation.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.matiu.pokebdemobile.R

private const val BankCardAspectRatio = 1.0f

@Composable
internal fun Card(
    rotationAngle: Float,
    modifier: Modifier = Modifier,
    pokemonName: String,
    color: Color,
    iconUpOrDown: Int?
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

    Box() {
        Box(
            modifier = sideModifier
                .graphicsLayer {
                    alpha = if (rotationAngle in 90f..180f) 0f else 1f
                }
                .background(Color.LightGray)
                .border(2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)),
        )

        Box(
            modifier = sideModifier
                .graphicsLayer {
                    alpha = if (rotationAngle in 90f..180f) 1f else 0f
                    rotationY = 180f
                }
                .background(color)
                .border(2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
        ) {

            Text(text = pokemonName, color = Color.Black, textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center))
            iconUpOrDown?.let {
                Icon(
                    painter = painterResource(iconUpOrDown),
                    contentDescription = "ikona",

                    modifier = Modifier.alpha(0.3f)
                )
            }

        }
    }
}