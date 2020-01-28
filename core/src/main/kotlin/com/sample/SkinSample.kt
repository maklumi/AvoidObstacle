package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class SkinSample : SampleBase() {

    companion object {
        private val log: Logger = Logger(SkinSample::class.java.simpleName, Logger.DEBUG)

        val SAMPLE_INFO = SampleInfo(SkinSample::class.java)
        private const val UI_SKIN = "assets/ui/uiskin.json"
        private const val WORLD_WIDTH = 1080f
        private const val WORLD_HEIGHT = 720f
    }

    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    private lateinit var assetManager: AssetManager
    private lateinit var stage: Stage
    private lateinit var skin: Skin

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        stage = Stage(viewport)

        assetManager = AssetManager()
        assetManager.load(UI_SKIN, Skin::class.java)
        assetManager.finishLoading()

        skin = assetManager[UI_SKIN]

        Gdx.input.inputProcessor = stage

        initUi()
    }

    private fun initUi() {
        val table = Table()
        table.defaults().pad(20f)

        for (i in 0..3) {
            val textButton = TextButton("Button $i", skin)
            textButton.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent, actor: Actor) {
                    log.debug("event= $event actor= $actor")
                }
            })
            table.add(textButton)
        }

        table.row()

        for (i in 0..1) {
            val checkBox = CheckBox("CheckBox $i", skin, "custom")
            checkBox.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent, actor: Actor) {
                    log.debug("event= $event actor= $actor")
                }
            })
            table.add(checkBox)
        }

        table.center()
        table.setFillParent(true)
        table.pack()

        stage.addActor(table)
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
    }
}