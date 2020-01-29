package com.obstacleavoid.screen

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames
import com.obstacleavoid.assets.UI_SKIN
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.Difficulty
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH

class OptionsScreen(game: AvoidObstacle) : MenuScreenBase(game) {

    private lateinit var checkBoxGroup: ButtonGroup<CheckBox>
    private lateinit var easy: CheckBox
    private lateinit var medium: CheckBox
    private lateinit var hard: CheckBox

    override fun createUi(): Actor {
        val gamePlayAtlas: TextureAtlas = assetManager.get(GAME_PLAY)
        val backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND)
        val uiskin = assetManager[UI_SKIN]

        val label = Label("DIFFICULTY", uiskin)
        label.setPosition(HUD_WIDTH / 2, HUD_HEIGHT / 2 + 180, Align.center)

        // checkbox has image cell & label cell
        easy = getCheckBox(Difficulty.EASY.name, uiskin)
        medium = getCheckBox(Difficulty.MEDIUM.name, uiskin)
        hard = getCheckBox(Difficulty.HARD.name, uiskin)

        checkBoxGroup = ButtonGroup(easy, medium, hard)
        checkBoxGroup.setChecked(GameManager.difficultyLevel.name)

        val backButton = TextButton("BACK", uiskin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                back()
            }
        })

        val changeListener = object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                // one uncheck become null, second one checked has text
                val difficultyText = checkBoxGroup.checked?.text?.toString() ?: return
                GameManager.difficultyLevel = Difficulty.valueOf(difficultyText)
            }
        }

        easy.addListener(changeListener)
        medium.addListener(changeListener)
        hard.addListener(changeListener)

        val contentTable = Table(uiskin)
        contentTable.defaults().pad(10f)
        contentTable.setBackground(RegionNames.PANEL)
        contentTable.add(label).row()
        contentTable.add(easy).row()
        contentTable.add(medium).row()
        contentTable.add(hard).row()
        contentTable.add(backButton)

        val table = Table()
        table.defaults().pad(15f)
        table.background = TextureRegionDrawable(backgroundRegion)
        table.add(contentTable)
        table.center()
        table.setFillParent(true)
        table.pack()
        return table
    }

    private fun back() {
        game.screen = MenuScreen(game)
    }

    private fun getCheckBox(text: String, skin: Skin): CheckBox {
        val cb = CheckBox(text, skin)
        cb.left().pad(8f) // bring all to left and put padding all around
        cb.labelCell.pad(8f) // pad second cell 8f all around
        return cb
    }
    // as3sfxr
}