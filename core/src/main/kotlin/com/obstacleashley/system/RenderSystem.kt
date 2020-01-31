package com.obstacleashley.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleashley.Dimension
import com.obstacleashley.Position
import com.obstacleashley.Texture
import com.obstacleashley.common.DIMENSION
import com.obstacleashley.common.POSITION
import com.obstacleashley.common.TEXTURE
import ktx.ashley.allOf
import ktx.ashley.get

class RenderSystem(
        private val viewport: Viewport,
        private val batch: SpriteBatch
) : EntitySystem() {

    private val family: Family = allOf(Texture::class, Position::class, Dimension::class).get()

    private val renderQueue = Array<Entity>()

    override fun update(deltaTime: Float) {
        val entities: ImmutableArray<Entity> = engine.getEntitiesFor(family)
        entities.forEach { renderQueue.add(it) }
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()

        renderGamePlay()

        batch.end()
        renderQueue.clear()
    }

    private fun renderGamePlay() {
        val entities = Array.ArrayIterable(renderQueue)
        for (entity in entities) {
            val tc = entity[TEXTURE]!!
            val pc = entity[POSITION]!!
            val dc = entity[DIMENSION]!!

            batch.draw(tc.region, pc.x, pc.y, dc.width, dc.height)
        }
    }

}