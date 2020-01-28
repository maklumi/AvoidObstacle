package com.sample.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Logger

class CustomActor(val region: TextureRegion) : Actor() {

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = color // so that fade in and out work
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    companion object {
        private val log = Logger(CustomActor::class.java.simpleName, Logger.DEBUG)
    }
}