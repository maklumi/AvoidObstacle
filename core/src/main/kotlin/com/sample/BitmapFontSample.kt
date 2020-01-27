package com.sample

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class BitmapFontSample : SampleBase() {

    companion object {
        val SAMPLE_INFO = SampleInfo(BitmapFontSample::class.java)
        private const val WIDTH = 1080f // world units
        private const val HEIGHT = 720f // world units
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var effectFont: BitmapFont
    private lateinit var uiFont: BitmapFont
    private val glyphLayout = GlyphLayout()

    override fun create() {
        camera = OrthographicCamera()
        viewport = FitViewport(WIDTH, HEIGHT, camera)
        batch = SpriteBatch()
        effectFont = BitmapFont(Gdx.files.internal("assets/font/french.fnt")) // fonts initialized inside create
        uiFont = BitmapFont(Gdx.files.internal("assets/font/oswald-32.fnt"))
        uiFont.data.markupEnabled = true // allow mark up color below
    }

    private fun draw() {
        val text1 = "USING BITMAP FONT"
        effectFont.draw(batch, text1, 20f, HEIGHT, 100f, 0, true)
        val text2 = "[#FF0000]BITMAP [GREEN]FONTS [YELLOW]ARE [BLUE]COOL!"
        glyphLayout.setText(uiFont, text2) // planning to center text as below
        uiFont.draw(batch, text2,
                    (WIDTH - glyphLayout.width) / 2f,
                    (HEIGHT - glyphLayout.height) / 2f
        )
    }

    override fun render() {
        batch.projectionMatrix = camera.combined
        batch.begin()
        draw()
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        effectFont.dispose()
        uiFont.dispose()
    }
}