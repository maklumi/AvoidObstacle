package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ArrayMap
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.*
import com.maklumi.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class ViewportSample : SampleBase() {

    companion object {
        val SAMPLE_INFO = SampleInfo(ViewportSample::class.java)
        private val log: Logger = Logger(ViewportSample::class.java.name, Logger.DEBUG)
        private const val WORLD_WIDTH = 800.0f // world units 4:3
        private const val WORLD_HEIGHT = 600.0f // world units 4:3
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var currentViewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var texture: Texture
    private lateinit var font: BitmapFont

    private val viewports = ArrayMap<String, Viewport>()
    private var currentIndex = -1
    private var currentViewportName = ""

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        camera = OrthographicCamera()
        batch = SpriteBatch()
        texture = Texture(Gdx.files.internal("raw/level-bg.png"))
        font = BitmapFont(Gdx.files.internal("font/oswald-32.fnt"))

        initializeViewportArrayMap()
        selectNextViewport()

        Gdx.input.inputProcessor = this
    }

    override fun render() {
        GdxUtils.clearScreen()
        batch.projectionMatrix = camera.combined
        batch.begin()
        draw()
        batch.end()
    }

    private fun draw() {
        batch.draw(texture, 0f, 0f, WORLD_WIDTH, WORLD_HEIGHT)
        font.draw(batch, currentViewportName, 50f, 100f)
    }

    override fun resize(width: Int, height: Int) {
        currentViewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        texture.dispose()
        font.dispose()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        selectNextViewport()
        return true
    }

    private fun initializeViewportArrayMap() {
        viewports.put(StretchViewport::class.java.simpleName,
                StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera))
        viewports.put(FitViewport::class.java.simpleName,
                FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera))
        viewports.put(FillViewport::class.java.simpleName,
                FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera))
        viewports.put(ScreenViewport::class.java.simpleName,
                ScreenViewport(camera))
        viewports.put(ExtendViewport::class.java.simpleName,
                ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera))
    }

    private fun selectNextViewport() {
        // (-1 + 1) % 5 = 0 % 5 = 0
        // (0 + 1) % 5 = 1 % 5 = 1
        // (1 + 1) % 5 = 2 % 5 = 2
        // (2 + 1) % 5 = 3 % 5 = 3
        // (3 + 1) % 5 = 4 % 5 = 4
        // (4 + 1) % 5 = 5 % 5 = 0
        // (0 + 1) % 5 = 1 % 5 = 1
        currentIndex = (currentIndex + 1) % viewports.size
        currentViewport = viewports.getValueAt(currentIndex)
        currentViewport.update(Gdx.graphics.width, Gdx.graphics.height)
        currentViewportName = viewports.getKeyAt(currentIndex)
        log.debug("selected viewport= $currentViewportName")
    }
}