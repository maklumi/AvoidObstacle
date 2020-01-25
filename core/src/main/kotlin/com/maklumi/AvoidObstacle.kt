package com.maklumi

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.maklumi.screen.FirstScreen

class AvoidObstacle : Game() {

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        screen = FirstScreen()
    }
}