package com.obstacleashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.obstacleashley.Movement
import com.obstacleashley.Position
import com.obstacleashley.common.MOVEMENT
import com.obstacleashley.common.POSITION
import ktx.ashley.allOf
import ktx.ashley.get

class MovementSystem : IteratingSystem(
        allOf(Position::class,
              Movement::class).get()
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val mc = entity[MOVEMENT]!!
        val pc = entity[POSITION]!!

        pc.x += mc.xSpeed
    }
}