package com.obstacleavoid.common

import com.badlogic.gdx.Gdx
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.config.Difficulty

object GameManager {

    private const val HIGH_SCORE_KEY = "high score"
    private const val DIFFICULTY = "difficulty"

    // on desktop the file is in user/.prefs
    private val preferences = Gdx.app.getPreferences(AvoidObstacle::class.java.simpleName)

    var highScore: Int = 0
        get() = preferences.getInteger(HIGH_SCORE_KEY, 0)
        set(value) {
            if (value > field) {
                field = value
                preferences.putInteger(HIGH_SCORE_KEY, field)
                preferences.flush()
            }
        }

    var difficultyLevel: Difficulty = Difficulty.EASY
        get() {
            val difficultyString = preferences.getString(DIFFICULTY, Difficulty.EASY.toString())
            return Difficulty.valueOf(difficultyString)
        }
        set(value) {
            field = value
            preferences.putString(DIFFICULTY, field.toString())
            preferences.flush()
        }

    var lives: Int = 3

    var scores: Int = 0

    val isGameOver: Boolean
        get() = lives <= 0

}