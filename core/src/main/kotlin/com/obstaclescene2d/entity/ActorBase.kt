package com.obstaclescene2d.entity

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Logger

open class ActorBase : Actor() {

    val circle = Circle(0f, 0f, 1f)
    var region: TextureRegion? = null

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (region == null) {
            log.debug("Region must not be null")
            return
        }
        // need to fill in all parameters otherwise it will be ignored if changed
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    override fun drawDebugBounds(shapes: ShapeRenderer) {
        super.drawDebugBounds(shapes)
        if (!debug) return
        if (stage != null) shapes.color = stage.debugColor
        shapes.x(circle.x, circle.y, 0.1f)
        shapes.circle(circle.x, circle.y, circle.radius, 24)
    }

    override fun positionChanged() {
        updateCollisionShape()
    }

    override fun sizeChanged() {
        updateCollisionShape()
    }

    private fun updateCollisionShape() {
        circle.setPosition(x + width / 2, y + height / 2)
    }

    companion object {
        private val log = Logger(ActorBase::class.java.simpleName, Logger.DEBUG)
    }
}