package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames
import com.obstacleavoid.assets.UI_SKIN


class MenuScreen(game: AvoidObstacle) : MenuScreenBase(game) {

    companion object {
        private val log = Logger(MenuScreen::class.java.simpleName, Logger.DEBUG)
    }

    override fun createUi(): Actor {
        val table = Table()
        val gamePlayAtlas: TextureAtlas = assetManager.get(GAME_PLAY)
        val uiskin = assetManager[UI_SKIN]
        val backgroundRegion: TextureRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
        table.background = TextureRegionDrawable(backgroundRegion)
        // play button
        val playButton = TextButton("PLAY", uiskin)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                play()
            }
        })
        // high score button
        val highScoreButton = TextButton("HIGHSCORE", uiskin)
        highScoreButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                showHighScore()
            }
        })
        // options button
        val optionsButton = TextButton("OPTIONS", uiskin)
        optionsButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                showOptions()
            }
        })

        val quitButton = TextButton("QUIT", uiskin)
        quitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Gdx.app.exit()
            }
        })

        // setup table
        val buttonTable = Table(uiskin) // use this skin for this table
        buttonTable.defaults().pad(20f)
        buttonTable.setBackground(RegionNames.PANEL) // so we use string here
        buttonTable.add(playButton).row()
        buttonTable.add(highScoreButton).row()
        buttonTable.add(optionsButton).row()
        buttonTable.add(quitButton).row()
        buttonTable.center()
        table.add(buttonTable)
        table.center()
        table.setFillParent(true)
        table.pack()
        return table
    }

    private fun play() {
        log.debug("play()")
        game.screen = com.obstacleashley.screen.GameScreen(game)
    }

    private fun showHighScore() {
        log.debug("showHighScore()")
        game.screen = HighScoreScreen(game)
    }

    private fun showOptions() {
        log.debug("showOptions()")
        game.screen = OptionsScreen(game)

    }

}