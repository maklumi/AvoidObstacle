package com.obstacleashley

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.utils.Pool

class Bounds(var bounds: Circle = Circle()) : Component

class PlayerTag : Component

class Movement(var xSpeed: Float = 0f, var ySpeed: Float = 0f) : Component, Pool.Poolable {
    override fun reset() {
        var xSpeed = 0f
        var ySpeed = 0f
    }
}

class Position(var x: Float = 0f, var y: Float = 0f) : Component

class WorldWrapTag : Component
class CleanUpTag : Component

class ObstacleTag(var isHit: Boolean = false) : Component, Pool.Poolable {
    override fun reset() {
        isHit = false
    }
}
