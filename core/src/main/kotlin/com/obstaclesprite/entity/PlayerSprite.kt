package com.obstaclesprite.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.obstacleavoid.config.PLAYER_SIZE

class PlayerSprite(region: TextureRegion) : GameObjectSpriteBase(region, PLAYER_SIZE / 2) {

    init {
        setSize(PLAYER_SIZE, PLAYER_SIZE)
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