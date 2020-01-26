package com.obstacleavoid

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.obstacleavoid.screen.GameScreen

class AvoidObstacle : Game() {

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        setScreen(GameScreen())
    }
}