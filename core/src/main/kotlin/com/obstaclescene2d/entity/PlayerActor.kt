package com.obstaclescene2d.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.obstacleavoid.config.PLAYER_SIZE
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH

class PlayerActor : ActorBase() {

    init {
        circle.radius = PLAYER_SIZE / 2
        setSize(PLAYER_SIZE, PLAYER_SIZE)
    }

    private val maxSpeedX = 0.4f

    override fun act(delta: Float) {
        update()
        super.act(delta)
    }

    private fun update() {
        var speedX = 0f
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) speedX = maxSpeedX
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) speedX = -maxSpeedX
        x += speedX
        blockPlayerFromLeavingWorld()
    }

    private fun blockPlayerFromLeavingWorld() {
        val playerX = MathUtils.clamp(x, 0f, WORLD_WIDTH - PLAYER_SIZE)
        val playerY = MathUtils.clamp(y, -2 * PLAYER_SIZE, WORLD_HEIGHT + 2 * PLAYER_SIZE)
        setPosition(playerX, playerY)
    }

}