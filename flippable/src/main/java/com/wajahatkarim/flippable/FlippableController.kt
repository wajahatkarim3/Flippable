package com.wajahatkarim.flippable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * A [FlippableController] which lets you control flipping programmatically.
 *
 * @author Wajahat Karim (https://wajahatkarim.com)
 */
class FlippableController {

    private val _flipRequests = MutableSharedFlow<FlippableState>(extraBufferCapacity = 1)
    internal val flipRequests = _flipRequests.asSharedFlow()

    private var _currentSide: FlippableState = FlippableState.FRONT
    private var _flipEnabled: Boolean = true

    /**
     * Flips the view to the [FlippableState.FRONT] side
     */
    fun flipToFront() {
        flip(FlippableState.FRONT)
    }

    /**
     * Flips the view to the [FlippableState.BACK] side
     */
    fun flipToBack() {
        flip(FlippableState.BACK)
    }

    /**
     * Flips the view to the passed [flippableState]
     * @param flippableState The side to flip the view to.
     */
    fun flip(flippableState: FlippableState) {
        if (_flipEnabled.not())
            return

        _currentSide = flippableState
        _flipRequests.tryEmit(flippableState)
    }

    /**
     * Flips the view to the other side. If it's [FlippableState.FRONT] it goes to [FlippableState.BACK] and vice-versa
     */
    fun flip() {
        if (_currentSide == FlippableState.FRONT)
            flipToBack()
        else flipToFront()
    }

    internal fun setConfig(
        flipEnabled: Boolean = true,
    ) {
        _flipEnabled = flipEnabled
    }
}

/**
 * Creates an instance of [FlippableController] and remembers it for recomposition.
 */
@Composable
fun rememberFlipController(): FlippableController {
    return remember {
        FlippableController()
    }
}