package com.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.GAME_PLAY

@Deprecated("")
class GameScreen(private val game: AvoidObstacle) : Screen {

    private val log = Logger(GameScreen::class.java.name, Logger.DEBUG)
    private val controller = GameController(game)
    private val assetManager = game.assetManager
    private lateinit var renderer: GameRenderer

    override fun show() {
        log.debug("show()")
        assetManager.load(FONT)
        assetManager.load(GAME_PLAY)
        assetManager.finishLoading()

        renderer = GameRenderer(game.batch, assetManager, controller)
    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.render(delta)

        // change screen should be in this class
        if (controller.isGameOver) {
            game.screen = MenuScreen(game)
        }
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
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