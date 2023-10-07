package com.wajahatkarim.flippable

/**
 * An Enum class to keep the state of side of [Flippable] like [FRONT] or [BACK]
 */
enum class FlippableState {
    INITIALIZED,
    FRONT,
    BACK
}

private const val ROTATION_MAX = 180f
private const val OPACITY_MAX = 1f
private const val ZERO_FLOAT = 0f

fun FlippableState.backStateRotation() = checkFlippableReturnValue(ROTATION_MAX, ZERO_FLOAT)
fun FlippableState.frontStateRotation() = checkFlippableReturnValue(ZERO_FLOAT, ROTATION_MAX)
fun FlippableState.backOpacity() = checkFlippableReturnValue(ZERO_FLOAT, OPACITY_MAX)
fun FlippableState.frontOpacity() = checkFlippableReturnValue(OPACITY_MAX, ZERO_FLOAT)
private fun FlippableState.checkFlippableReturnValue(
    frontValue: Float,
    backValue: Float
) = when (this) {
    FlippableState.INITIALIZED, FlippableState.FRONT -> frontValue
    FlippableState.BACK -> backValue
}