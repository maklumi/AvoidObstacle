package com.obstacleavoid

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.screen.GameScreen
import com.obstacleavoid.screen.LoadingScreen
import kotlin.properties.Delegates

class AvoidObstacle : Game() {

    val assetManager = AssetManager()
    lateinit var batch: SpriteBatch

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        assetManager.logger.level = Logger.DEBUG
        batch = SpriteBatch()
        setScreen(LoadingScreen(this))
    }

    override fun dispose() {
        assetManager.dispose()
        batch.dispose()
        super.dispose()
    }

}