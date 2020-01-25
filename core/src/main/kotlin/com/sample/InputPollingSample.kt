package com.sample

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.sample.common.SampleBase
import com.sample.common.SampleInfo

class InputPollingSample : SampleBase() {

    companion object {
        val SAMPLE_INFO = SampleInfo(InputPollingSample::class.java)
    }

    val camera = OrthographicCamera()
    val viewport = FitViewport(1000f, 800f, camera)
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont(Gdx.files.internal("font/oswald-32.fnt"))
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = camera.combined
        batch.begin()
        pollInputAndDraw()
        batch.end()

    }

    private fun pollInputAndDraw() {
        val x = Gdx.input.x
        val y = Gdx.input.y

        val l = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
        val r = Gdx.input.isButtonPressed(Input.Buttons.RIGHT)

        font.draw(batch, "Mouse ($x,$y)", 100f, 700f)
        font.draw(batch, "${if (l) "left " else "left not"} pressed", 100f, 750f)
        font.draw(batch, "${if (r) "right " else "right not"} pressed", 100f, 800f)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}