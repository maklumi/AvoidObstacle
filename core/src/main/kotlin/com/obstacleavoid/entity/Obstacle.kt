package com.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Obstacle : GameObjectBase(.3f) {

    var isAlreadyHit = false

    fun drawObstacleDebug(r: ShapeRenderer) {
        r.circle(bounds.x, bounds.y, bounds.radius, 24)
    }

    fun update(speed: Float) {
        y -= speed
    }
}