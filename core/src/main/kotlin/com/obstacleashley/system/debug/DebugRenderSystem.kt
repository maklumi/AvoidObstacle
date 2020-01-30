package com.obstacleashley.system.debug

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleashley.Bounds
import com.obstacleashley.common.boundsMapper
import ktx.ashley.allOf
import ktx.ashley.get

class DebugRenderSystem(
        private val viewport: Viewport,
        private val renderer: ShapeRenderer
) : IteratingSystem(
        allOf(Bounds::class).get()
) {

    override fun update(deltaTime: Float) {
        val oldColor = renderer.color.cpy()
        viewport.apply()
        renderer.projectionMatrix = viewport.camera.combined
        renderer.color = Color.GOLD
        renderer.begin(ShapeRenderer.ShapeType.Line)

        super.update(deltaTime) // will process entity below

        renderer.end()
        renderer.color = oldColor
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val bc: Bounds = entity[boundsMapper] ?: return
        renderer.circle(bc.bounds.x, bc.bounds.y, bc.bounds.radius, 20)
    }

}