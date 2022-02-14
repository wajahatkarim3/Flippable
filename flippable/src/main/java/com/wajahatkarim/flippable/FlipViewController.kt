package com.wajahatkarim.flippable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * A [FlipViewController] which lets you control flipping programmatically.
 *
 * @author Wajahat Karim (https://wajahatkarim.com)
 */
class FlipViewController internal constructor() {

    private val _flipRequests = MutableSharedFlow<FlipViewState>(extraBufferCapacity = 1)
    internal val flipRequests = _flipRequests.asSharedFlow()

    private var _currentSide: FlipViewState = FlipViewState.FRONT
    private var _flipEnabled: Boolean = true

    /**
     * Flips the view to the [FlipViewState.FRONT] side
     */
    fun flipToFront() {
        flip(FlipViewState.FRONT)
    }

    /**
     * Flips the view to the [FlipViewState.BACK] side
     */
    fun flipToBack() {
        flip(FlipViewState.BACK)
    }

    /**
     * Flips the view to the passed [flipViewState]
     * @param flipViewState The side to flip the view to.
     */
    fun flip(flipViewState: FlipViewState) {
        if (_flipEnabled.not())
            return

        _currentSide = flipViewState
        _flipRequests.tryEmit(flipViewState)
    }

    /**
     * Flips the view to the other side. If it's [FlipViewState.FRONT] it goes to [FlipViewState.BACK] and vice-versa
     */
    fun flip() {
        if (_currentSide == FlipViewState.FRONT)
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
 * Creates an instance of [FlipViewController] and remembers it for recomposition.
 */
@Composable
fun rememberFlipController(): FlipViewController {
    return remember {
        FlipViewController()
    }
}