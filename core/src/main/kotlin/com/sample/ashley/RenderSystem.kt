package com.sample.ashley

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.Viewport


class RenderSystem(private val viewport: Viewport, private val batch: SpriteBatch) : EntitySystem() {

    companion object {
        private val log = Logger(RenderSystem::class.java.name, Logger.DEBUG)

        val FAMILY: Family = Family.all(
                PositionComponent::class.java,
                SizeComponent::class.java,
                TextureComponent::class.java
        ).get()

        private val POSITION = ComponentMapper.getFor(PositionComponent::class.java)

        private val SIZE = ComponentMapper.getFor(SizeComponent::class.java)

        private val TEXTURE = ComponentMapper.getFor(TextureComponent::class.java)
    }

    private lateinit var entities: ImmutableArray<Entity>

    override fun addedToEngine(engine: Engine) {
        log.debug("added to engine")
        entities = engine.getEntitiesFor(FAMILY)
        log.debug("family entities= " + entities.size())
        log.debug("all entities= " + engine.entities.size())
    }

    override fun update(deltaTime: Float) { // called every frame
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        draw()
        batch.end()
    }

    private fun draw() {
        for (entity in entities) {
            val position = POSITION[entity]
            val size = SIZE[entity]
            val texture: TextureComponent = TEXTURE.get(entity)
            batch.draw(texture.texture,
                       position.x, position.y,
                       size.width, size.height
            )
        }
    }
}