package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo

class SpriteBatchSample : SampleBase() {

    companion object {
        val SAMPLE_INFO = SampleInfo(SpriteBatchSample::class.java)
        const val WORLD_WIDTH = 10.8f // world units 1080 / 100 = 10.8
        const val WORLD_HEIGHT = 7.2f // world units 720 / 100 = 7.2
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var texture: Texture
    private var oldColor = Color()

    private val width = 1f // world units
    private val height = 1f // world units

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        batch = SpriteBatch()
        texture = Texture(Gdx.files.internal("raw/character.png"))
    }

    override fun render() {
        GdxUtils.clearScreen()
        batch.projectionMatrix = camera.combined
        batch.begin()
        draw()
        batch.end()
    }

    private fun draw() {
        batch.draw(texture,
                1f, 1f,                             // x, y,
                width / 2f, height / 2f,    // originX, originY,
                width, height,                            // width, height
                1.0f, 1.0f,                   // scaleX, scaleY,
                0.0f,                              // rotation
                0, 0,                            // srcX, srcY
                texture.width, texture.height,             // srcWidth, srcHeight
                false, false                     // flipX, flipY
        )

        // render scaled character
        batch.draw(texture,
                4f, 2f,
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f,
                0.0f,
                0, 0,
                texture.width, texture.height,
                false, true
        )

        // save batch color
        oldColor.set(batch.color)

        // set color to sprite batch
        batch.color = Color.GREEN

        // render green character
        batch.draw(texture,
                8f, 1f,
                width / 2f, height / 2f,
                width, height,
                1.0f, 1.0f,
                0.0f,
                0, 0,
                texture.width, texture.height,
                false, false
        )

        batch.color = oldColor
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        texture.dispose()
    }
}