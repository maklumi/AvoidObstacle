package com.obstaclesprite.entity

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH

open class GameObjectSpriteBase(region: TextureRegion, var radius: Float) : Sprite(region) {

    val bounds: Circle
        get() = Circle(x + radius, y + radius, radius)


    override fun setX(x: Float) {
        val tempX = MathUtils.clamp(x, 0f, WORLD_WIDTH - 2 * radius)
        super.setX(tempX)
    }

    override fun setPosition(x: Float, y: Float) {
        this.x = MathUtils.clamp(x, 0f, WORLD_WIDTH - 2 * radius)
        this.y = MathUtils.clamp(y, -4 * radius, WORLD_HEIGHT + 4 * radius)
    }

    fun drawDebug(r: ShapeRenderer) {
        r.x(bounds.x, bounds.y, 0.1f)
        r.circle(bounds.x, bounds.y, bounds.radius, 24)
    }

}