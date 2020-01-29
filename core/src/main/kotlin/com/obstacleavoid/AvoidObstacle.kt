package com.obstacleavoid

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.screen.LoadingScreen

class AvoidObstacle : Game() {

    val assetManager = AssetManager()
    lateinit var batch: SpriteBatch

    override fun create() {
        Gdx.app.logLevel = Application.LOG_ERROR
        assetManager.logger.level = Logger.ERROR
        batch = SpriteBatch()
        setScreen(LoadingScreen(this))
    }

    override fun dispose() {
        assetManager.dispose()
        batch.dispose()
        super.dispose()
    }

}