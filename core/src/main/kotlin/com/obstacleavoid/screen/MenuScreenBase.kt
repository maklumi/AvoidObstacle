package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.util.GdxUtils

abstract class MenuScreenBase(protected val game: AvoidObstacle) : ScreenAdapter() {

    protected val camera = OrthographicCamera()
    protected val viewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, camera)

    protected val assetManager = game.assetManager
    protected lateinit var stage: Stage

    override fun show() {
        stage = Stage(viewport, game.batch)
        Gdx.input.inputProcessor = stage

        stage.addActor(createUi())
    }

    protected abstract fun createUi(): Actor

    override fun render(delta: Float) {
        GdxUtils.clearScreen()

        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
    }

}