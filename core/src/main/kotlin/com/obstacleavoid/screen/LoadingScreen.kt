package com.obstacleavoid.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.HIT_SOUND
import com.obstacleavoid.assets.UI_SKIN
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.util.GdxUtils


class LoadingScreen(private val game: AvoidObstacle) : ScreenAdapter() {

    companion object {
        private val log: Logger = Logger(LoadingScreen::class.java.simpleName, Logger.DEBUG)
        private const val PROGRESS_BAR_WIDTH: Float = HUD_WIDTH / 2f // world units
        private const val PROGRESS_BAR_HEIGHT = 60f // world units
    }

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private var renderer = ShapeRenderer()

    private val assetManager = game.assetManager
    private var progress = 0f
    private var waitTime = .75f
    private var changeScreen = false

    override fun show() {
        log.debug("show")
        camera = OrthographicCamera()
        viewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, camera)
        renderer = ShapeRenderer()
        assetManager.load(FONT)
        assetManager.load(GAME_PLAY)
        assetManager.load(UI_SKIN)
        assetManager.load(HIT_SOUND)
    }

    override fun render(delta: Float) {
        update(delta)
        GdxUtils.clearScreen()
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        draw()
        renderer.end()
        if (changeScreen) {
            game.screen = MenuScreen(game)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        log.debug("hide")
        // NOTE: screens dont dispose automatically
        dispose()
    }

    override fun dispose() {
        log.debug("dispose")
        renderer.dispose()
    }

    private fun update(delta: Float) { // progress is between 0 and 1
        progress = assetManager.progress
        // update returns true when all assets are loaded
        if (assetManager.update()) {
            waitTime -= delta
            if (waitTime <= 0) {
                changeScreen = true
            }
        }
    }

    private fun draw() {
        val progressBarX: Float = (HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f
        val progressBarY: Float = (HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f
        renderer.rect(progressBarX, progressBarY,
                      progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT
        )
    }

    private fun waitMillis(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}