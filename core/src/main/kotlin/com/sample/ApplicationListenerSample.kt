package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Logger

class ApplicationListenerSample : ApplicationListener {

    private val logger = Logger(javaClass.simpleName, Logger.DEBUG)

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        logger.debug("create")
    }

    override fun render() {
    }

    override fun resize(width: Int, height: Int) {
        logger.debug("resize")
    }

    override fun pause() {
        logger.debug("pause")
    }

    override fun resume() {
        logger.debug("resume")
    }

    override fun dispose() {
        logger.debug("dispose")
    }
}