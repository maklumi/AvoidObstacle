package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.entity.Obstacle
import com.obstacleavoid.util.GdxUtils
import com.obstacleavoid.util.ViewportUtils
import com.obstacleavoid.util.debug.DebugCameraController

class GameRenderer(private val controller: GameLogic) {

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val uiFont = BitmapFont(Gdx.files.internal("assets/font/ui_font_32.fnt"))
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, hudCamera)
    private val glyphLayout = GlyphLayout()
    private val batch = SpriteBatch()

    fun render(delta: Float) {
        GdxUtils.clearScreen()
        renderUI()
        drawDebug(delta)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)
        ViewportUtils.debugPixelPerUnit(viewport)
    }

    fun dispose() {
        renderer.dispose()
        batch.dispose()
        uiFont.dispose()
    }

    private fun renderUI() {
        hudViewport.apply() // because using multiple viewports
        batch.projectionMatrix = hudCamera.combined
        batch.begin()
        glyphLayout.setText(uiFont, controller.textLives)
        uiFont.draw(batch, controller.textLives, 20f, HUD_HEIGHT - glyphLayout.height)
        glyphLayout.setText(uiFont, controller.textScore)
        uiFont.draw(batch, controller.textScore, HUD_WIDTH - glyphLayout.width - 20f, HUD_HEIGHT - glyphLayout.height)
        batch.end()
    }


    private fun drawObstaclesDebug() {
        renderer.begin(ShapeRenderer.ShapeType.Line)
        val iterable = Array.ArrayIterable<Obstacle>(controller.obstacles)
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
        controller.player.drawPlayerDebug(renderer)
        controller.player.update()
    }

}