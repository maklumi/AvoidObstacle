package com.obstacleashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleashley.Position
import com.obstacleashley.WorldWrapTag
import com.obstacleashley.common.POSITION
import ktx.ashley.allOf
import ktx.ashley.get

class WorldWrapSystem(private val viewport: Viewport) : IteratingSystem(
        allOf(
                WorldWrapTag::class,
                Position::class
        ).get()
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val posC = entity[POSITION]!!
        posC.x = MathUtils.clamp(posC.x, 0f, viewport.worldWidth)
        posC.y = MathUtils.clamp(posC.y, 0f, viewport.worldHeight)
    }
}