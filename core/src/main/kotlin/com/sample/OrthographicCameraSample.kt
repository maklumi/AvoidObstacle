package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.maklumi.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class OrthographicCameraSample : SampleBase() {

    private val log = Logger(OrthographicCameraSample::class.java.name, Logger.DEBUG)

    companion object{
        val SAMPLE_INFO = SampleInfo(OrthographicCameraSample::class.java)
        const val WORLD_WIDTH = 10.8f // world units 1080 / 100 = 10.8
        const val WORLD_HEIGHT = 7.2f // world units 720 / 100 = 7.2
        private const val CAMERA_SPEED = 2.0f // world units
        private const val CAMERA_ZOOM_SPEED = 2.0f // world units
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var texture: Texture

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        batch = SpriteBatch()
        texture = Texture(Gdx.files.internal("raw/level-bg.png"))
    }

    override fun render() {
        queryInput()
        GdxUtils.clearScreen()
        // rendering
        batch.projectionMatrix = camera.combined
        batch.begin()
        draw()
        batch.end()
    }

    private fun queryInput() { // deltaTime is time passed between two frames
        val deltaTime = Gdx.graphics.deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= CAMERA_SPEED * deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += CAMERA_SPEED * deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += CAMERA_SPEED * deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= CAMERA_SPEED * deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            log.debug("position= " + camera.position)
            log.debug("zoom= " + camera.zoom)
        }
        camera.update()
    }

    private fun draw() {
        batch.draw(texture, 0f, 0f,  // x, y
                WORLD_WIDTH, WORLD_HEIGHT
        )
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        texture.dispose()
    }

}