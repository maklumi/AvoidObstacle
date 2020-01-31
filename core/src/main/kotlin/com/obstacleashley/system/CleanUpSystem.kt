package com.obstacleashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.obstacleashley.CleanUpTag
import com.obstacleashley.Position
import com.obstacleashley.common.POSITION
import ktx.ashley.allOf
import ktx.ashley.get

class CleanUpSystem : IteratingSystem(allOf(
        CleanUpTag::class,
        Position::class
).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val posC = entity[POSITION]!!
        if (posC.y < -1f) {
            engine.removeEntity(entity)
        }

    }
}