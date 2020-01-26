package com.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Obstacle : GameObjectBase(.3f) {

    fun drawObstacleDebug(r: ShapeRenderer) {
        r.circle(bounds.x, bounds.y, bounds.radius, 24)
    }

    fun update() {
        y -= MAX_SPEED_Y
    }

    companion object {
        private const val MAX_SPEED_Y = 0.1f
    }
}