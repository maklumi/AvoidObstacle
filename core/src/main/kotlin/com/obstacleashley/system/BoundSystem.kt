package com.obstacleashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.obstacleashley.Bounds
import com.obstacleashley.Dimension
import com.obstacleashley.Position
import com.obstacleashley.common.DIMENSION
import com.obstacleashley.common.POSITION
import com.obstacleashley.common.boundsMapper
import ktx.ashley.allOf
import ktx.ashley.get

class BoundSystem : IteratingSystem(allOf(
        Bounds::class,
        Dimension::class,
        Position::class
).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val bc = entity[boundsMapper]!!
        val pc = entity[POSITION]!!
        val dc = entity[DIMENSION]!!

        bc.bounds.x = pc.x + dc.width / 2
        bc.bounds.y = pc.y + dc.height / 2
    }
}