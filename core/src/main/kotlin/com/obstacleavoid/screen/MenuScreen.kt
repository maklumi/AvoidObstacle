package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames
import com.obstacleavoid.assets.UI_TEXTURE_ATLAS
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.util.GdxUtils


class MenuScreen(private val game: AvoidObstacle) : ScreenAdapter() {

    companion object {
        private val log = Logger(MenuScreen::class.java.simpleName, Logger.DEBUG)
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
        val uiAtlas: TextureAtlas = assetManager.get(UI_TEXTURE_ATLAS)
        val panelRegion = uiAtlas.findRegion(RegionNames.PANEL)
        val backgroundRegion: TextureRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
        table.background = TextureRegionDrawable(backgroundRegion)
        // play button
        val playButton = createButton(uiAtlas, RegionNames.PLAY, RegionNames.PLAY_PRESSED)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                play()
            }
        })
        // high score button
        val highScoreButton = createButton(uiAtlas, RegionNames.HIGH_SCORE, RegionNames.HIGH_SCORE_PRESSED)
        highScoreButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                showHighScore()
            }
        })
        // options button
        val optionsButton = createButton(uiAtlas, RegionNames.OPTIONS, RegionNames.OPTIONS_PRESSED)
        optionsButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                showOptions()
            }
        })

        // setup table
        val buttonTable = Table()
        buttonTable.defaults().pad(20f)
        buttonTable.background = TextureRegionDrawable(panelRegion)
        buttonTable.add(playButton).row()
        buttonTable.add(highScoreButton).row()
        buttonTable.add(optionsButton).row()
        buttonTable.center()
        table.add(buttonTable)
        table.center()
        table.setFillParent(true)
        table.pack()
        return table
    }

    private fun createButton(atlas: TextureAtlas, up: String, down: String): ImageButton {
        val upRegion = atlas.findRegion(up)
        val downRegion = atlas.findRegion(down)
        return ImageButton(TextureRegionDrawable(upRegion), TextureRegionDrawable(downRegion))
    }

    private fun play() {
        log.debug("play()")
        game.screen = GameScreen(game)
    }

    private fun showHighScore() {
        log.debug("showHighScore()")

    }

    private fun showOptions() {
        log.debug("showOptions()")

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