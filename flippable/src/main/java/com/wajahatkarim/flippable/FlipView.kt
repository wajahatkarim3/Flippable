package com.wajahatkarim.flippable

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class FlipViewState {
    INITIALIZED,
    FRONT,
    BACK
}

/**
 *  A composable which creates a card-like flip view for [frontSide] and [backSide] composables.
 *
 *  Example usage:
 *
 *  ```
 *  FlipView(
 *      flipDurationMs = 400,
 *      frontSide = {
 *          // Composable content
 *      },
 *      backSide = {
 *          // Composable content
 *      })
 *  ```
 *
 *  @param frontSide [Composable] method to draw any view for the front side
 *  @param backSide [Composable] method to draw any view for the back side
 *  @param flipDurationMs The duration in Milliseconds for the flipping animation
 */
@Composable
fun FlipView(
    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    flipDurationMs: Int = 1000,
    flipOnTouch: Boolean = true,
    flipEnabled: Boolean = true,
    autoFlip: Boolean = false,
    autoFlipDurationMs: Int = 1000,
    onFlippedListener: (currentSide: FlipViewState) -> Unit = {_, -> }
) {
    var prevViewState by remember { mutableStateOf(FlipViewState.INITIALIZED) }
    var flipViewState by remember { mutableStateOf(FlipViewState.INITIALIZED) }
    val transition: Transition<FlipViewState> = updateTransition(
        targetState = flipViewState,
        label = "Flip Transition",
    )

    val flipCall: () -> Unit = {
        if (transition.isRunning.not() && flipEnabled && flipOnTouch) {
            prevViewState = flipViewState
            flipViewState =
                if (flipViewState == FlipViewState.FRONT)
                    FlipViewState.BACK
                else FlipViewState.FRONT
        }
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = transition.currentState, block = {
        if (transition.currentState == FlipViewState.INITIALIZED) {
            prevViewState = FlipViewState.INITIALIZED
            flipViewState = FlipViewState.FRONT
            return@LaunchedEffect
        }

        if (prevViewState != FlipViewState.INITIALIZED && transition.currentState == flipViewState) {
            onFlippedListener.invoke(flipViewState)

            if (autoFlip && flipViewState != FlipViewState.FRONT) {
                scope.launch {
                    delay(autoFlipDurationMs.toLong())
                    flipCall()
                }
            }
        }
    })

    val frontRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlipViewState.FRONT isTransitioningTo FlipViewState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        90f at flipDurationMs / 2
                        90f at flipDurationMs
                    }
                }

                FlipViewState.BACK isTransitioningTo FlipViewState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        90f at 0
                        90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Rotation"
    ) { state ->
        when(state) {
            FlipViewState.INITIALIZED, FlipViewState.FRONT -> 0f
            FlipViewState.BACK -> 180f
        }
    }

    val backRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlipViewState.FRONT isTransitioningTo FlipViewState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        -90f at 0
                        -90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                FlipViewState.BACK isTransitioningTo FlipViewState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        -90f at flipDurationMs / 2
                        -90f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Rotation"
    ) { state ->
        when(state) {
            FlipViewState.INITIALIZED, FlipViewState.FRONT -> 180f
            FlipViewState.BACK -> 0f
        }
    }

    val frontOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlipViewState.FRONT isTransitioningTo FlipViewState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                FlipViewState.BACK isTransitioningTo FlipViewState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Opacity"
    ) { state ->
        when(state) {
            FlipViewState.INITIALIZED, FlipViewState.FRONT -> 1f
            FlipViewState.BACK -> 0f
        }
    }

    val backOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlipViewState.FRONT isTransitioningTo FlipViewState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                FlipViewState.BACK isTransitioningTo FlipViewState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Opacity"
    ) { state ->
        when(state) {
            FlipViewState.INITIALIZED, FlipViewState.FRONT -> 0f
            FlipViewState.BACK -> 1f
        }
    }

    Box(
        modifier = modifier
            .clickable(
                onClick = {
                    flipCall()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = contentAlignment
    ) {

        Box(modifier = Modifier
            .graphicsLayer {
                rotationY = backRotation
            }
            .alpha(backOpacity)
        ) {
            backSide()
        }

        Box(modifier = Modifier
            .graphicsLayer(
                rotationY = frontRotation
            )
            .alpha(frontOpacity)
        ) {
            frontSide()
        }
    }
}