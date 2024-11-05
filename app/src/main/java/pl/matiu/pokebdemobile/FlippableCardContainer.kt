package pl.matiu.pokebdemobile

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun FlippableCardContainer(pokemonName: String) {

    var targetAngle by remember { mutableStateOf(0f) }

    val rotation by animateFloatAsState(
        targetValue = targetAngle,
        animationSpec = tween(
            durationMillis = 1500,
            easing = LinearEasing
        )
    )

    LaunchedEffect(Unit) {
        targetAngle=180f
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp),
            rotationAngle = rotation,
            pokemonName = pokemonName
        )
    }
}
