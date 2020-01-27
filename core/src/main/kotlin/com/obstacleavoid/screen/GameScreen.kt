package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.config.*
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
    private var lives = 3
    private val textLives: String
        get() = "LIVES: $lives"
    private var score = 0
    private var displayedScore = 0
    private var timer = 0f
    private val textScore: String
        get() = "SCORE: $displayedScore"
    private val uiFont = BitmapFont(Gdx.files.internal("assets/font/ui_font_32.fnt"))
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, hudCamera)
    private val glyphLayout = GlyphLayout()
    private val batch = SpriteBatch()
    private var level = Difficulty.HARD

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
        updateObstacles(delta)
        updateScoreAndLives(delta)
        renderUI()
        drawDebug(delta)
    }

    private fun renderUI() {
        batch.projectionMatrix = hudCamera.combined
        batch.begin()
        glyphLayout.setText(uiFont, textLives)
        uiFont.draw(batch, textLives, 20f, HUD_HEIGHT - glyphLayout.height)
        glyphLayout.setText(uiFont, textScore)
        uiFont.draw(batch, textScore, HUD_WIDTH - glyphLayout.width - 20f, HUD_HEIGHT - glyphLayout.height)
        batch.end()
    }

    private fun updateScoreAndLives(delta: Float) {
        timer += delta
        if (timer > 2f) {
            timer = 0f
            score += MathUtils.random(10, 30)
        }

        smoothOutScoreDisplay(delta)

        if (isCollision()) {
            lives--
        }
    }

    private fun smoothOutScoreDisplay(delta: Float) {
        if (displayedScore < score) {
//            displayedScore = Math.min(score, displayedScore + (delta * 60).toInt()) // is same as below
            displayedScore = score.coerceAtMost(displayedScore + (delta * 60).toInt())
        }
    }

    private fun isCollision(): Boolean {
        var isOverlap = false
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            if (obstacle.isAlreadyHit) continue
            isOverlap = Intersector.overlaps(player.bounds, obstacle.bounds)
            if (isOverlap) {
                obstacle.isAlreadyHit = true
                return true
            }
        }
        return isOverlap
    }

    private fun updateObstacles(delta: Float) {
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            obstacle.update(level.speed)
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
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
        batch.dispose()
        uiFont.dispose()
    }
}