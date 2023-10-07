package com.wajahatkarim.flippable

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.KeyframesSpec
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
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 *  A composable which creates a card-like flip view for [frontSide] and [backSide] composables.
 *
 *  Example usage:
 *
 *  ```
 *  Flippable(
 *      frontSide = {
 *          // Composable content
 *      },
 *      backSide = {
 *          // Composable content
 *      }),
 *      flipController = rememberFlipController(),
 *      // ... other optional parameters
 *  ```
 *
 *  @param frontSide [Composable] method to draw any view for the front side
 *  @param backSide [Composable] method to draw any view for the back side
 *  @param flipController A [FlippableController] which lets you control flipping programmatically.
 *  @param modifier The Modifier for this [Flippable]
 *  @param contentAlignment The [Flippable] is contained in a [Box], so this tells the alignment to organize both Front and Back side composable.
 *  @param flipDurationMs The duration in Milliseconds for the flipping animation
 *  @param flipOnTouch If true, flipping will be done through clicking the Front/Back sides.
 *  @param flipEnabled Enable/Disable the Flipping animation.
 *  @param autoFlip If true, the [Flippable] will automatically flip back after [autoFlipDurationMs].
 *  @param autoFlipDurationMs The duration in Milliseconds to auto-flip back
 *  @param cameraDistance The [GraphicsLayerScope.cameraDistance] for the flip animation. Sets the distance along the Z axis (orthogonal to the X/Y plane on which layers are drawn) from the camera to this layer.
 *  @param flipAnimationType The animation type of flipping effect.
 *  @param onFlippedListener The listener which is triggered when flipping animation is finished.
 *
 *  @author Wajahat Karim (https://wajahatkarim.com)
 */
@Composable
fun Flippable(
    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit,
    flipController: FlippableController,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    flipDurationMs: Int = 400,
    flipOnTouch: Boolean = true,
    flipEnabled: Boolean = true,
    autoFlip: Boolean = false,
    autoFlipDurationMs: Int = 1000,
    cameraDistance: Float = 30.0F,
    flipAnimationType: FlipAnimationType = FlipAnimationType.HORIZONTAL_CLOCKWISE,
    onFlippedListener: (currentSide: FlippableState) -> Unit = { _ -> }
) {
    var prevViewState by remember { mutableStateOf(FlippableState.INITIALIZED) }
    var flippableState by remember { mutableStateOf(FlippableState.INITIALIZED) }
    val transition: Transition<FlippableState> = updateTransition(
        targetState = flippableState,
        label = "Flip Transition",
    )
    flipController.setConfig(
        flipEnabled = flipEnabled
    )

    LaunchedEffect(key1 = flipController, block = {
        flipController.flipRequests
            .onEach {
                prevViewState = flippableState
                flippableState = it
            }
            .launchIn(this)
    })

    val flipCall: () -> Unit = {
        if (transition.isRunning.not() && flipEnabled) {
            prevViewState = flippableState
            if (flippableState == FlippableState.FRONT)
                flipController.flipToBack()
            else flipController.flipToFront()
        }
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = transition.currentState, block = {
        if (transition.currentState == FlippableState.INITIALIZED) {
            prevViewState = FlippableState.INITIALIZED
            flippableState = FlippableState.FRONT
            return@LaunchedEffect
        }

        if (prevViewState != FlippableState.INITIALIZED && transition.currentState == flippableState) {
            onFlippedListener.invoke(flippableState)

            if (autoFlip && flippableState != FlippableState.FRONT) {
                scope.launch {
                    delay(autoFlipDurationMs.toLong())
                    flipCall()
                }
            }
        }
    })

    val frontRotation: Float by frontRotationAnimate(transition, flipDurationMs)
    val backRotation: Float by backRotationAnimate(transition, flipDurationMs)
    val frontOpacity: Float by frontOpacityAnimate(transition, flipDurationMs)
    val backOpacity: Float by backOpacityAnimate(transition, flipDurationMs)

    Box(
        modifier = modifier
            .clickable(
                enabled = flipOnTouch,
                onClick = {
                    flipCall()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = contentAlignment
    ) {

        BaseFlippable(
            cameraDistance,
            flipAnimationType,
            backRotation,
            backOpacity,
            backSide,
            frontRotation,
            frontOpacity,
            frontSide
        )
    }
}

@Composable
private fun BaseFlippable(
    cameraDistance: Float,
    flipAnimationType: FlipAnimationType,
    backRotation: Float,
    backOpacity: Float,
    backSide: @Composable () -> Unit,
    frontRotation: Float,
    frontOpacity: Float,
    frontSide: @Composable () -> Unit
) {
    BoxSideFlippable(cameraDistance, flipAnimationType, backRotation, backOpacity) {
        backSide()
    }

    BoxSideFlippable(cameraDistance, flipAnimationType, frontRotation, frontOpacity) {
        frontSide()
    }
}


@Composable
private fun BoxSideFlippable(
    cameraDistance: Float,
    flipAnimationType: FlipAnimationType,
    rotation: Float,
    opacity: Float,
    boxSide: @Composable () -> Unit
) {
    Box(modifier = Modifier
        .graphicsLayer {
            this.cameraDistance = cameraDistance
            rotation(flipAnimationType, rotation)
        }
        .alpha(opacity)
        .zIndex(1F - opacity)
    ) {
        boxSide()
    }
}

private fun GraphicsLayerScope.rotation(
    flipAnimationType: FlipAnimationType,
    rotation: Float
) {
    when (flipAnimationType) {
        FlipAnimationType.HORIZONTAL_CLOCKWISE -> rotationY = rotation
        FlipAnimationType.HORIZONTAL_ANTI_CLOCKWISE -> rotationY = -rotation
        FlipAnimationType.VERTICAL_CLOCKWISE -> rotationX = rotation
        FlipAnimationType.VERTICAL_ANTI_CLOCKWISE -> rotationX = -rotation
    }
}
 
@Composable
private fun backRotationAnimate(
    transition: Transition<FlippableState>,
    flipDurationMs: Int
) = transition.animateFloat(
    transitionSpec = backRotationTransition(flipDurationMs),
    label = "Back Rotation"
) { state -> state.backStateRotation() }

@Composable
private fun frontRotationAnimate(
    transition: Transition<FlippableState>,
    flipDurationMs: Int
) = transition.animateFloat(
    transitionSpec = frontRotationTransition(flipDurationMs),
    label = "Front Rotation"
) { state -> state.frontStateRotation() }

@Composable
private fun backOpacityAnimate(
    transition: Transition<FlippableState>,
    flipDurationMs: Int
) = transition.animateFloat(
    transitionSpec = {
        when {
            FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                upKeyframesSpec(flipDurationMs)
            }

            FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                downKeyframesSpec(flipDurationMs)
            }

            else -> snap()
        }
    },
    label = "Back Opacity"
) { state -> state.backOpacity() }


@Composable
private fun frontOpacityAnimate(
    transition: Transition<FlippableState>,
    flipDurationMs: Int
) = transition.animateFloat(
    transitionSpec = {
        when {
            FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                downKeyframesSpec(flipDurationMs)
            }

            FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                upKeyframesSpec(flipDurationMs)
            }

            else -> snap()
        }
    },
    label = "Front Opacity"
) { state -> state.frontOpacity() }


private fun downKeyframesSpec(flipDurationMs: Int): KeyframesSpec<Float> = keyframes {
    durationMillis = flipDurationMs
    1f at 0
    1f at flipDurationMs.half().ancestors()
    0f at flipDurationMs.half()
    0f at flipDurationMs
}

private fun upKeyframesSpec(flipDurationMs: Int): KeyframesSpec<Float> = keyframes {
    durationMillis = flipDurationMs
    0f at 0
    0f at flipDurationMs.half().ancestors()
    1f at flipDurationMs.half()
    1f at flipDurationMs
}

@Composable
private fun backRotationTransition(
    flipDurationMs: Int
): @Composable() (Transition.Segment<FlippableState>.() -> FiniteAnimationSpec<Float>) = {
    when {
        FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
            keyframes {
                durationMillis = flipDurationMs
                -90f at 0
                -90f at flipDurationMs.half()
                0f at flipDurationMs
            }
        }

        FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
            keyframes {
                durationMillis = flipDurationMs
                0f at 0
                -90f at flipDurationMs.half()
                -90f at flipDurationMs
            }
        }

        else -> snap()
    }
}

@Composable
private fun frontRotationTransition(
    flipDurationMs: Int
): @Composable() (Transition.Segment<FlippableState>.() -> FiniteAnimationSpec<Float>) = {
    when {
        FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
            keyframes {
                durationMillis = flipDurationMs
                0f at 0
                90f at flipDurationMs.half()
                90f at flipDurationMs
            }
        }

        FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
            keyframes {
                durationMillis = flipDurationMs
                90f at 0
                90f at flipDurationMs.half()
                0f at flipDurationMs
            }
        }

        else -> snap()
    }
}


private fun Int.half(): Int = this / 2
private fun Int.ancestors(): Int = this - 1
 