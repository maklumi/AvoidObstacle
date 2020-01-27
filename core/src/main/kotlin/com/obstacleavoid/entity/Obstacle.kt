package com.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Pool

class Obstacle : GameObjectBase(.3f), Pool.Poolable {

    var isAlreadyHit = false

    fun drawObstacleDebug(r: ShapeRenderer) {
        r.circle(bounds.x, bounds.y, bounds.radius, 24)
        r.x(bounds.x, bounds.y, 0.1f)
    }

    fun update(speed: Float) {
        y -= speed
    }

    override fun reset() {
        isAlreadyHit = false
    }

}