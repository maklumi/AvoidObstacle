package com.obstacleashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.obstacleashley.Movement
import com.obstacleashley.PlayerTag
import com.obstacleashley.common.MOVEMENT
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerSystem : IteratingSystem(
        allOf(PlayerTag::class,
              Movement::class).get()
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val movement = entity[MOVEMENT]!!
        movement.xSpeed = 0f
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.xSpeed += MAX_SPEED_X
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.xSpeed -= MAX_SPEED_X
        }
        movement.ySpeed = 0f
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.ySpeed += MAX_SPEED_X
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.ySpeed -= MAX_SPEED_X
        }
    }

    companion object {
        private const val MAX_SPEED_X = 0.4f
    }
}