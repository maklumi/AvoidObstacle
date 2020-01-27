package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.assets.BACKGROUND
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.OBSTACLE
import com.obstacleavoid.assets.PLAYER
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.entity.Background
import com.obstacleavoid.entity.Obstacle
import com.obstacleavoid.util.GdxUtils
import com.obstacleavoid.util.ViewportUtils
import com.obstacleavoid.util.debug.DebugCameraController

class GameRenderer(assetManager: AssetManager, private val controller: GameLogic) {

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val uiFont = assetManager.get(FONT)
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, hudCamera)
    private val glyphLayout = GlyphLayout()
    private val batch = SpriteBatch()

    private val playerTex = assetManager.get(PLAYER)
    private val obstacleTex = assetManager.get(OBSTACLE)
    private val player = controller.player
    private val obstacles = controller.obstacles
    private val background = Background()

    init {
        background.texture = assetManager.get(BACKGROUND)
    }

    fun render(delta: Float) {
        GdxUtils.clearScreen()
        renderGamePlay()
        checkTouch()
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
    }

    private fun renderGamePlay() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()

        batch.draw(background.texture, background.x, background.y, background.width, background.height)

        batch.draw(playerTex, player.x, player.y, player.radius * 2, player.radius * 2)

        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            batch.draw(obstacleTex, obstacle.x, obstacle.y, obstacle.radius * 2, obstacle.radius * 2)
        }

        batch.end()
    }

    private fun checkTouch() {
        if (Gdx.input.isTouched && !controller.isGameOver) {
            val screenPosition = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            val worldPosition = viewport.unproject(Vector2(screenPosition))
            player.x = worldPosition.x
//            println("screen $screenPosition")
//            println("world $worldPosition")
        }
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