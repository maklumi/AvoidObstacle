package com.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.Logger

class GameScreen : Screen {

    private val log = Logger(GameScreen::class.java.name, Logger.DEBUG)
    private val controller = GameLogic()
    private val renderer = GameRenderer(controller)

    override fun show() {
        log.debug("show()")
    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.render(delta)
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