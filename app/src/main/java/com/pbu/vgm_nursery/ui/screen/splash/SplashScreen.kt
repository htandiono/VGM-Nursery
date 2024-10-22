package com.pbu.vgm_nursery.ui.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pbu.vgm_nursery.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.rotate
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun SplashScreen(
    onTimeOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentOnTimeOut by rememberUpdatedState(onTimeOut)
    val rotation = remember { Animatable(0f) } // Animatable to store rotation angle

    LaunchedEffect(Unit) {
        delay(2.seconds)
        currentOnTimeOut()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val animatedPainter = rememberAnimatedVectorPainter(
            animatedImageVector = AnimatedImageVector.animatedVectorResource(id = R.drawable.animated_loading),
            atEnd = false
        )

        Image(
            painter = animatedPainter,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
                .rotate(rotation.value) // Apply rotation to the modifier
        )

        LaunchedEffect(Unit) {
            launch {
                while (isActive) { // Loop while composable is active
                    rotation.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 2000, easing = LinearEasing) // Animation for rotation
                    )
                    rotation.snapTo(0f) // Reset rotation after reaching 360 degrees
                }
            }
        }
    }
}