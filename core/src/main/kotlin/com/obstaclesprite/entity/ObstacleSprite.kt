package com.obstaclesprite.entity

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import com.obstacleavoid.config.OBSTACLE_SIZE

class ObstacleSprite(region: TextureRegion) :
        GameObjectSpriteBase(region, OBSTACLE_SIZE / 2),
        Pool.Poolable {

    init {
        setSize(OBSTACLE_SIZE, OBSTACLE_SIZE)
    }

    var isAlreadyHit = false

    override fun reset() {
        isAlreadyHit = false
    }

    fun update(speed: Float) {
        y -= speed
    }
}