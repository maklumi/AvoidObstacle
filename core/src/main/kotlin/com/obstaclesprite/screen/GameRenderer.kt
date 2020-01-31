package com.obstaclesprite.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames.BACKGROUND
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.entity.Background
import com.obstacleavoid.util.GdxUtils
import com.obstacleavoid.util.ViewportUtils
import com.obstacleavoid.util.debug.DebugCameraController
import com.obstaclesprite.entity.ObstacleSprite

class GameRenderer(private val batch: SpriteBatch, assetManager: AssetManager, private val controller: GameController) {

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val uiFont = assetManager.get(FONT)
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, hudCamera)
    private val glyphLayout = GlyphLayout()

    private val gameAtlas = assetManager[GAME_PLAY]
    private val player = controller.player
    private val obstacles = controller.obstacles
    private val background = Background()

    init {
        background.texture = gameAtlas.findRegion(BACKGROUND)
    }

    fun render(delta: Float) {
        GdxUtils.clearScreen()
        player.update()
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
    }

    private fun renderGamePlay() {
        viewport.apply()
        batch.projectionMatrix = camera.combined
        batch.begin()

        player.draw(batch)

        val iterable = Array.ArrayIterable<ObstacleSprite>(obstacles)
        for (obstacle in iterable) {
            obstacle.draw(batch)
        }

        batch.end()
    }

    private fun checkTouch() {
        if (Gdx.input.isTouched && !controller.isGameOver) {
            val screenPosition = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            val worldPosition = viewport.unproject(Vector2(screenPosition))
            player.x = worldPosition.x
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


    private fun drawPlayerAndObstaclesDebug() {
        viewport.apply()
        val oldColor = renderer.color.cpy()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)

        controller.player.drawDebug(renderer)

        val iterable = Array.ArrayIterable<ObstacleSprite>(controller.obstacles)
        for (obstacle in iterable) {
            obstacle.drawDebug(renderer)
        }

        renderer.end()
        renderer.color = oldColor
    }

    private fun drawDebug(delta: Float) {
        DebugCameraController.handleDebugInput(delta)
        DebugCameraController.applyTo(camera)
        ViewportUtils.drawGrid(viewport, renderer)
        drawPlayerAndObstaclesDebug()
    }

}