package com.wajahatkarim.flippable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FlipViewController internal constructor() {

    private val _flipRequests = MutableSharedFlow<FlipViewState>(extraBufferCapacity = 1)
    internal val flipRequests = _flipRequests.asSharedFlow()

    private var _currentSide: FlipViewState = FlipViewState.FRONT
    private var _flipEnabled: Boolean = true

    fun flipToFront() {
        flip(FlipViewState.FRONT)
    }

    fun flipToBack() {
        flip(FlipViewState.BACK)
    }

    fun flip(flipViewState: FlipViewState) {
        if (_flipEnabled.not())
            return

        _currentSide = flipViewState
        _flipRequests.tryEmit(flipViewState)
    }

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

@Composable
fun rememberFlipController(): FlipViewController {
    return remember {
        FlipViewController()
    }
}