package pl.matiu.pokebdemobile

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
internal fun FlippableCardContainer(pokemonName: String, timeToLaunchCard: Long, color: Color) {

    var targetAngle by remember { mutableStateOf(0f) }

    val rotation by animateFloatAsState(
        targetValue = targetAngle,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing
        )
    )

    LaunchedEffect(Unit) {
        delay(timeToLaunchCard)
        targetAngle=180f
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier,
//                .padding(horizontal = 5.dp),
            rotationAngle = rotation,
            pokemonName = pokemonName,
            color = color
        )
    }
}
