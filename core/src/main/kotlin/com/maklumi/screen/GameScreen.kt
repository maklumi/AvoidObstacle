package com.maklumi.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.maklumi.config.WORLD_HEIGHT
import com.maklumi.config.WORLD_WIDTH
import com.maklumi.util.GdxUtils
import com.maklumi.util.ViewportUtils

class GameScreen : Screen {

    private val log = Logger(GameScreen::class.java.simpleName, Logger.DEBUG)
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    override fun show() {
        log.debug("show()")
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
    }

    override fun render(delta: Float) {
        GdxUtils.clearScreen()
        drawDebug()
    }

    private fun drawDebug() {
        ViewportUtils.drawGrid(viewport, renderer)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}