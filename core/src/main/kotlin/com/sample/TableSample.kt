package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.util.GdxUtils
import com.sample.common.CustomActor
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class TableSample : SampleBase() {

    companion object {
        private val log: Logger = Logger(TableSample::class.java.simpleName, Logger.DEBUG)

        val SAMPLE_INFO = SampleInfo(TableSample::class.java)

        private const val WORLD_WIDTH = 1080f
        private const val WORLD_HEIGHT = 720f
    }

    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)

    private lateinit var texture: Texture
    private lateinit var stage: Stage

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        stage = Stage(viewport)
        texture = Texture(Gdx.files.internal("raw/custom-actor.png"))
        initUi()
    }

    private fun initUi() {
        log.debug("Table is a group with lots of functions")
        val table = Table()
        table.defaults().space(20f)
        for (i in 0..5) {
            val customActor = CustomActor(TextureRegion(texture))
            // need to set size, default width/height is 0
            customActor.setSize(180f, 60f)
            table.add(customActor)
            table.row()
        }
        table.row()
        val actor = CustomActor(TextureRegion(texture))
        actor.setSize(200f, 40f)
        table.add(actor).fillX().expandX().left()
        table.row()
        table.center()
        table.setFillParent(true)
        table.pack()
        stage.addActor(table)
        stage.isDebugAll = true // red is cell
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