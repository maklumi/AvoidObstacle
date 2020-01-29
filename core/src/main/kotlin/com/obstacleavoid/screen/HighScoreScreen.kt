package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames
import com.obstacleavoid.assets.UI_SKIN
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.util.GdxUtils

class HighScoreScreen(private val game: AvoidObstacle) : ScreenAdapter() {

    companion object {
        private val log = Logger(HighScoreScreen::class.java.simpleName, Logger.DEBUG)
    }

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, camera)
    private val renderer = ShapeRenderer()

    private val assetManager = game.assetManager
    private lateinit var stage: Stage

    override fun show() {
        log.debug("show")
        stage = Stage(viewport, game.batch)
        Gdx.input.inputProcessor = stage

        stage.addActor(createUi())
    }

    private fun createUi(): Actor {
        val table = Table()

        val gamePlayAtlas: TextureAtlas = assetManager.get(GAME_PLAY)
        val backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
        val uiskin = assetManager[UI_SKIN]

        val backButton = TextButton("BACK", uiskin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                back()
            }
        })

        table.background = TextureRegionDrawable(backgroundRegion)

        val highScoreText = Label("HIGH SCORE", uiskin)
        val highScoreLabel = Label("${GameManager.highScore}", uiskin)

        // setup table
        val contentTable = Table(uiskin) // remember to put skin to use here also
        contentTable.defaults().pad(20f)
        contentTable.setBackground(RegionNames.PANEL)
        contentTable.add(highScoreText).row()
        contentTable.add(highScoreLabel).row()
        contentTable.add(backButton).row()
        contentTable.center()

        table.add(contentTable)
        table.center()
        table.setFillParent(true)
        table.pack()
        return table
    }

    private fun back() {
        log.debug("back()")
        game.screen = MenuScreen(game)
    }

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
        renderer.dispose()
    }

}