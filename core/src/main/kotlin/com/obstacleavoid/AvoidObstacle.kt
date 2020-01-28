package com.obstacleavoid

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.screen.GameScreen
import com.obstacleavoid.screen.LoadingScreen

class AvoidObstacle : Game() {

    val assetManager = AssetManager()

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
//        assetManager.logger.level = Logger.DEBUG

        setScreen(LoadingScreen(this))
    }

    override fun dispose() {
        assetManager.dispose()
        super.dispose()
    }

}