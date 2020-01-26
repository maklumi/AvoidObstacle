package com.obstacleavoid.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle

class Player {

    var x = 0f
    var y = 0f
    var radius = 1f


    val bounds: Circle = Circle(x, y, radius)
        get() {
            field.setPosition(x, y)
            return field
        }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun drawPlayerDebug(r: ShapeRenderer) {
        r.begin(ShapeRenderer.ShapeType.Line)
        r.circle(bounds.x, bounds.y, bounds.radius, 24)
        r.end()
    }

    fun update() {
        var speedX = 0f
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) speedX = MAX_SPEED_X
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) speedX = -MAX_SPEED_X
        x += speedX
    }

    companion object {
        private const val MAX_SPEED_X = 0.4f
    }
}