package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class AssetManagerSample : SampleBase() {

    companion object {
        val SAMPLE_INFO = SampleInfo(AssetManagerSample::class.java)
        private const val BACKGROUND_BLUE = "assets/raw/background-blue.png"
        private const val GREEN_CIRCLE = "assets/raw/circle-green.png"
        private const val RED_CIRCLE = "assets/raw/circle-red.png"
        private const val FONT = "assets/font/oswald-32.fnt"
    }

    private val assetManager = AssetManager()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(1080f, 720f, camera)
    private lateinit var batch: SpriteBatch // always init inside create()

    private var backgroundBlue: Texture? = null
    private var greenCircle: Texture? = null
    private var redCircle: Texture? = null
    private var font: BitmapFont? = null

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        batch = SpriteBatch()

        assetManager.logger.level = Logger.DEBUG

        // load assets
        assetManager.load(BACKGROUND_BLUE, Texture::class.java)
        assetManager.load(GREEN_CIRCLE, Texture::class.java)
        assetManager.load(RED_CIRCLE, Texture::class.java)
        assetManager.load(FONT, BitmapFont::class.java)
        // blocks until all resources are loaded into memory
        assetManager.finishLoading()
        // get assets
        backgroundBlue = assetManager[BACKGROUND_BLUE]
        greenCircle = assetManager[GREEN_CIRCLE]
        redCircle = assetManager[RED_CIRCLE]
        font = assetManager[FONT]
    }

    override fun render() {
        GdxUtils.clearScreen()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(backgroundBlue, 0f, 0f)
        batch.draw(greenCircle, 50f, 50f)
        batch.draw(redCircle, 200f, 200f)
        font!!.draw(batch, "AssetManagerSample", 500f, 50f)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        batch.dispose()
        assetManager.dispose()
    }
}