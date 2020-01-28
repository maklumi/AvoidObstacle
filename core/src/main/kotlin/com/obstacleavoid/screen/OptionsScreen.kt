package com.obstacleavoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames
import com.obstacleavoid.assets.UI_TEXTURE_ATLAS
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.Difficulty
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.util.GdxUtils

class OptionsScreen(private val game: AvoidObstacle) : ScreenAdapter() {

    companion object {
        private val log = Logger(OptionsScreen::class.java.simpleName, Logger.DEBUG)
    }

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, camera)
    private val renderer = ShapeRenderer()

    private val assetManager = game.assetManager
    private lateinit var stage: Stage
    private lateinit var checkMark: Image

    override fun show() {
        log.debug("show")
        stage = Stage(viewport, game.batch)
        Gdx.input.inputProcessor = stage

        createUi()
    }

    private fun createUi() {
        val gamePlayAtlas: TextureAtlas = assetManager.get(GAME_PLAY)
        val backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
        val background = Image(backgroundRegion)

        val font = assetManager.get(FONT)

        val uiAtlas: TextureAtlas = assetManager.get(UI_TEXTURE_ATLAS)

        val labelStyle = Label.LabelStyle(font, Color.WHITE)
        val label = Label("DIFFICULTY", labelStyle)
        label.setPosition(HUD_WIDTH / 2, HUD_HEIGHT / 2 + 180, Align.center)
        val easy = createButton(uiAtlas, RegionNames.EASY)
        easy.setPosition(HUD_WIDTH / 2, HUD_HEIGHT / 2 + 90, Align.center)
        easy.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                checkMark.y = easy.y + 25
                GameManager.difficultyLevel = Difficulty.EASY
            }
        })

        val medium = createButton(uiAtlas, RegionNames.MEDIUM)
        medium.setPosition(HUD_WIDTH / 2, HUD_HEIGHT / 2, Align.center)
        medium.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                checkMark.y = medium.y + 25
                GameManager.difficultyLevel = Difficulty.MEDIUM
            }
        })

        val hard = createButton(uiAtlas, RegionNames.HARD)
        hard.setPosition(HUD_WIDTH / 2, HUD_HEIGHT / 2 - 90, Align.center)
        hard.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                checkMark.y = hard.y + 25
                GameManager.difficultyLevel = Difficulty.HARD
            }
        })

        val checkMarkRegion = uiAtlas.findRegion(RegionNames.CHECK_MARK)
        checkMark = Image(TextureRegionDrawable(checkMarkRegion))
        checkMark.setPosition(medium.x + 50, medium.y + 40, Align.center)
        when (GameManager.difficultyLevel) {
            Difficulty.EASY -> checkMark.y = easy.y + 25
            Difficulty.MEDIUM -> checkMark.y = medium.y + 25
            Difficulty.HARD -> checkMark.y = hard.y + 25
        }


        val backRegion = uiAtlas.findRegion(RegionNames.BACK)
        val backPressedRegion = uiAtlas.findRegion(RegionNames.BACK_PRESSED)
        val backButton = ImageButton(TextureRegionDrawable(backRegion), TextureRegionDrawable(backPressedRegion))
        backButton.setPosition(HUD_WIDTH / 2, HUD_HEIGHT / 2 - 180, Align.center)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                back()
            }
        })

        stage.addActor(background)
        stage.addActor(easy)
        stage.addActor(medium)
        stage.addActor(hard)
        stage.addActor(checkMark)
        stage.addActor(backButton)
    }

    private fun createButton(atlas: TextureAtlas, name: String): ImageButton {
        val region = atlas.findRegion(name)
        return ImageButton(TextureRegionDrawable(region), TextureRegionDrawable(region))
    }

    private fun back() {
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