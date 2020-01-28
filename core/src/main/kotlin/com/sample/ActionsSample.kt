package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.util.GdxUtils
import com.sample.common.CustomActor
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class ActionsSample : SampleBase() {

    companion object {
        private val log: Logger = Logger(ActionsSample::class.java.simpleName, Logger.DEBUG)

        val SAMPLE_INFO = SampleInfo(ActionsSample::class.java)

        private const val WORLD_WIDTH = 1080f
        private const val WORLD_HEIGHT = 720f
    }

    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)

    private lateinit var texture: Texture
    private lateinit var customActor: CustomActor

    private lateinit var stage: Stage


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        stage = Stage(viewport) // must be created here
        texture = Texture(Gdx.files.internal("raw/custom-actor.png"))
        customActor = CustomActor(TextureRegion(texture))
        customActor.setSize(160f, 80f)
        customActor.setPosition(
                (WORLD_WIDTH - customActor.width) / 2f,
                (WORLD_HEIGHT - customActor.height) / 2f
        )

        stage.addActor(customActor)
        Gdx.input.inputProcessor = this

        val ls = System.getProperty("line.separator")
        val tab = "\t"

        log.debug(ls + "Press keys." + ls +
                          tab + "1 - RotateBy Action" + ls +
                          tab + "2 - FadeOut Action" + ls +
                          tab + "3 - FadeIn Action" + ls +
                          tab + "4 - ScaleTo Action" + ls +
                          tab + "5 - MoveTo Action" + ls +
                          tab + "6 - Sequential Action" + ls +
                          tab + "7 - Parallel Action"
        )
    }

    override fun keyDown(keycode: Int): Boolean {
        customActor.clearActions()
        when (keycode) {
            NUM_1 -> {
                log.debug("RotateBy Action")
                customActor.addAction(rotateBy(90f, 1f))
            }
            NUM_2 -> {
                log.debug("FadeOut Action")
                customActor.addAction(fadeOut(2f))
            }
            NUM_3 -> {
                log.debug("FadeIn Action")
                customActor.addAction(fadeIn(2f))
            }
            NUM_4 -> {
                log.debug("ScaleTo Action")
                customActor.addAction(scaleTo(1.5f, 1.5f, 2f))
            }
            NUM_5 -> {
                log.debug("MoveTo Action")
                customActor.addAction(moveTo(100f, 100f, 3f))
            }
            NUM_6 -> {
                log.debug("Sequential Action")
                val action: Action = sequence(
                        fadeOut(1f),
                        fadeIn(0.5f)
                )
                customActor.addAction(action)
            }
            NUM_7 -> {
                log.debug("Parallel Action")
                val action: Action = parallel(
                        rotateBy(90f, 2f),
                        moveBy(50f, 50f, 2f)
                )
                customActor.addAction(action)
            }
        }
        return true
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // no need to call setProjectionMatrix, begin/end, everything is handled internally
        stage.act()
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
        texture.dispose()
    }
}