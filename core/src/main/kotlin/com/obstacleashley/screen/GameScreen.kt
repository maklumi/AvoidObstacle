package com.obstacleashley.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleashley.system.debug.GridRenderSystem
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.util.GdxUtils

class GameScreen(game: AvoidObstacle) : Screen {

    private val log = Logger(GameScreen::class.java.name, Logger.DEBUG)

    private val assetManager = game.assetManager
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private val engine = PooledEngine()

    override fun show() {
        log.debug("show()")
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        renderer = ShapeRenderer()

        engine.addSystem(GridRenderSystem(viewport, renderer))
    }

    override fun render(delta: Float) {
        GdxUtils.clearScreen()
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}