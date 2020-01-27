package com.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.entity.Obstacle
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
    private val obstacles = Array<Obstacle>()
    private var spawnTimer = 0f

    override fun show() {
        log.debug("show()")
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        player.setPosition(1.5f, 3.2f)
        DebugCameraController.setStartPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
    }

    override fun render(delta: Float) {
        if (isCollision()) {
            return
        }
        GdxUtils.clearScreen()
        updateObstacles(delta)
        drawDebug(delta)
    }

    private fun isCollision(): Boolean {
        var isOverlap = false
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            isOverlap = Intersector.overlaps(player.bounds, obstacle.bounds)
            if (isOverlap) return isOverlap
        }
        return isOverlap
    }

    private fun updateObstacles(delta: Float) {
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            obstacle.update()
        }
        createNewObstacle(delta)
    }

    private fun createNewObstacle(delta: Float) {
        spawnTimer += delta
        if (spawnTimer > .5f) {
            val obstacle = Obstacle()
            val x = MathUtils.random(obstacle.radius, WORLD_WIDTH - obstacle.radius)
            val y = WORLD_HEIGHT + obstacle.radius * 2
            obstacle.setPosition(x, y)
            obstacles.add(obstacle)
            spawnTimer = 0f
        }
    }

    private fun drawObstaclesDebug() {
        renderer.begin(ShapeRenderer.ShapeType.Line)
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            obstacle.drawObstacleDebug(renderer)
        }
        renderer.end()
    }

    private fun drawDebug(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)
        ViewportUtils.drawGrid(viewport, renderer)
        drawPlayerDebug()
        drawObstaclesDebug()
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