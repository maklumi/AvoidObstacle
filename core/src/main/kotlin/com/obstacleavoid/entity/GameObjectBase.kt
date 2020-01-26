package com.obstacleavoid.entity

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH

open class GameObjectBase(var radius: Float) {

    var x = 0f
        set(value) {
            field = value
            field = MathUtils.clamp(field, 0f, WORLD_WIDTH - 2 * radius)
        }

    var y = 0f
        set(value) {
            field = value
            field = MathUtils.clamp(field, -4 * radius, WORLD_HEIGHT + 4 * radius)
        }

    val bounds: Circle = Circle(x, y, radius)
        get() {
            field.setPosition(x + radius, y + radius)
            return field
        }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

}