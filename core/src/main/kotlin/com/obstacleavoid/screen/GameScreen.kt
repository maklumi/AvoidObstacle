package com.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.entity.Player
import com.obstacleavoid.util.GdxUtils
import com.obstacleavoid.util.ViewportUtils
import com.obstacleavoid.util.debug.DebugCameraController

class GameScreen : Screen {

    private val log = Logger(GameScreen::class.java.simpleName, Logger.DEBUG)
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer
    private val player = Player()

    override fun show() {
        log.debug("show()")
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        player.setPosition(1.5f, 3.2f)
        DebugCameraController.setStartPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
    }

    override fun render(delta: Float) {
        GdxUtils.clearScreen()
        drawDebug(delta)
    }

    private fun drawDebug(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)
        ViewportUtils.drawGrid(viewport, renderer)
        drawPlayerDebug()
    }

    private fun drawPlayerDebug() {
        player.drawPlayerDebug(renderer)
        player.update()
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