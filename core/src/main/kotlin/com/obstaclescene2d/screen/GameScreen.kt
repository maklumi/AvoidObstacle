package com.obstaclescene2d.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.util.GdxUtils
import com.obstacleavoid.util.ViewportUtils
import com.obstacleavoid.util.debug.DebugCameraController

class GameScreen(game: AvoidObstacle) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val batch = game.batch
    private val glyphLayout = GlyphLayout()

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val uiCamera = OrthographicCamera()
    private val uiViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, uiCamera)
    private val uiFont = assetManager.get(FONT)

    private var lives = 3
    private var displayedScore = 0


    override fun show() {
        DebugCameraController.setStartPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
    }

    override fun render(delta: Float) {
        DebugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        GdxUtils.clearScreen()

        viewport.apply()
        renderGamePlay()

        uiViewport.apply()
        renderUi()

        viewport.apply()
        renderDebug()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        uiViewport.update(width, height, true)

        ViewportUtils.debugPixelPerUnit(viewport)
    }

    override fun dispose() {
        renderer.dispose()
    }

    private fun renderGamePlay() {
        batch.projectionMatrix = camera.combined
        batch.begin()

        drawGamePlay()

        batch.end()
    }

    private fun drawGamePlay() {

    }

    private fun renderUi() {
        batch.projectionMatrix = uiCamera.combined
        batch.begin()

        val textLives = "LIVES: $lives"
        glyphLayout.setText(uiFont, textLives)
        uiFont.draw(batch, textLives, 20f, HUD_HEIGHT - glyphLayout.height)

        val textScore = "SCORE: $displayedScore"
        glyphLayout.setText(uiFont, textScore)
        uiFont.draw(batch, textScore, HUD_WIDTH - glyphLayout.width - 20f, HUD_HEIGHT - glyphLayout.height)

        batch.end()
    }

    private fun renderDebug() {
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)


        renderer.end()

        ViewportUtils.drawGrid(viewport, renderer)
    }
}