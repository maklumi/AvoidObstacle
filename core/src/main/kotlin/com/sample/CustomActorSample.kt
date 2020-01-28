package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.util.GdxUtils
import com.sample.common.CustomActor
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class CustomActorSample : SampleBase() {

    companion object {
        private val log: Logger = Logger(CustomActorSample::class.java.simpleName, Logger.DEBUG)

        val SAMPLE_INFO = SampleInfo(CustomActorSample::class.java)

        private const val WORLD_WIDTH = 1080f
        private const val WORLD_HEIGHT = 720f
    }

    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)

    private lateinit var stage: Stage

    private lateinit var texture: Texture


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        stage = Stage(viewport) // must be created here
        texture = Texture(Gdx.files.internal("raw/custom-actor.png"))
        val customActor = CustomActor(TextureRegion(texture))
        customActor.setSize(160f, 80f)
        customActor.setPosition(
                (WORLD_WIDTH - customActor.width) / 2f,
                (WORLD_HEIGHT - customActor.height) / 2f
        )
        customActor.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                log.debug("customActor clicked event= $event x= $x y= $y")
            }
        })
        stage.addActor(customActor)
        Gdx.input.inputProcessor = stage
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        GdxUtils.clearScreen()
        // no need to call setProjectionMatrix, begin/end, everything is handled internally
        stage.act()
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
        texture.dispose()
    }
}