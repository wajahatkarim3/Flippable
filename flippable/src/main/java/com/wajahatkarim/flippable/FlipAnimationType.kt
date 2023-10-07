package com.wajahatkarim.flippable

/**
 * An Enum class for animation type of [Flippable]. It has these 4 states:
 * [HORIZONTAL_CLOCKWISE], [HORIZONTAL_ANTI_CLOCKWISE], [VERTICAL_CLOCKWISE], and [VERTICAL_ANTI_CLOCKWISE]
 */
enum class FlipAnimationType {
    /**
     * Rotates the [Flippable] horizontally in the clockwise direction
     */
    HORIZONTAL_CLOCKWISE,

    /**
     * Rotates the [Flippable] horizontally in the anti-clockwise direction
     */
    HORIZONTAL_ANTI_CLOCKWISE,

    /**
     * Rotates the [Flippable] vertically in the clockwise direction
     */
    VERTICAL_CLOCKWISE,

    /**
     * Rotates the [Flippable] vertically in the anti-clockwise direction
     */
    VERTICAL_ANTI_CLOCKWISE
}