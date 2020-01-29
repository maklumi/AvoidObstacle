package com.sample

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family.all
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.util.GdxUtils
import com.sample.ashley.PositionComponent
import com.sample.ashley.RenderSystem
import com.sample.ashley.SizeComponent
import com.sample.ashley.TextureComponent
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class AshleySystemSample : SampleBase() {

    companion object {
        private val log = Logger(AshleySystemSample::class.java.name, Logger.DEBUG)

        val SAMPLE_INFO = SampleInfo(AshleySystemSample::class.java)

        private const val WORLD_WIDTH = 10.8f // world units

        private const val WORLD_HEIGHT = 7.2f

        private const val LEVEL_BG = "raw/level-bg.png"
        private const val CHARACTER = "raw/character.png"

        private val FAMILY = all(TextureComponent::class.java).get()
    }

    private lateinit var assetManager: AssetManager
    private lateinit var viewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var engine: Engine

    private lateinit var entity: Entity
    private lateinit var texture: TextureComponent


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        assetManager = AssetManager()
        assetManager.logger.level = Logger.DEBUG
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        batch = SpriteBatch()
        engine = Engine()
        assetManager.load(LEVEL_BG, Texture::class.java)
        assetManager.load(CHARACTER, Texture::class.java)
        assetManager.finishLoading()
        Gdx.input.inputProcessor = this
        addBackground()
        addCharacter()
        engine.addEntityListener(FAMILY, object : EntityListener {
            override fun entityAdded(entity: Entity) {
                log.debug("family size= " + engine.getEntitiesFor(FAMILY).size())
                log.debug("total size= " + engine.entities.size())
                log.debug("entity added to family")
            }

            override fun entityRemoved(entity: Entity) {
                log.debug("family size= " + engine.getEntitiesFor(FAMILY).size())
                log.debug("total size= " + engine.entities.size())
                log.debug("entity removed from family")
            }
        })
        engine.addSystem(RenderSystem(viewport, batch))
    }

    private fun addBackground() {
        val position = PositionComponent()
        position.x = 0f
        position.y = 0f
        val size = SizeComponent()
        size.width = WORLD_WIDTH
        size.height = WORLD_HEIGHT
        val texture = TextureComponent()
        texture.texture = assetManager.get(LEVEL_BG)
        // composing background entity (game object)
        // background has position, size, texture
        val entity = Entity()
        entity.add(position)
        entity.add(size)
        entity.add(texture)
        engine.addEntity(entity)
    }

    private fun addCharacter() {
        val position = PositionComponent()
        position.x = 0f
        position.y = 0f
        val size = SizeComponent()
        size.width = 2f
        size.height = 2f
        texture = TextureComponent()
        texture.texture = assetManager.get(CHARACTER)
        // composing background entity (game object)
        // background has position, size, texture
        entity = Entity()
        entity.add(position)
        entity.add(size)
        entity.add(texture)
        engine.addEntity(entity)
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.R) {
            if (FAMILY.matches(entity)) {
                entity.remove(TextureComponent::class.java)
            }
        } else if (keycode == Input.Keys.A) {
            if (!FAMILY.matches(entity)) {
                entity.add(texture)
            }
        }
        return true
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        GdxUtils.clearScreen()
        val delta = Gdx.graphics.deltaTime
        engine.update(delta)
    }

    override fun dispose() {
        assetManager.dispose()
        batch.dispose()
    }
}