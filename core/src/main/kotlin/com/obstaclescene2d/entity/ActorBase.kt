package com.obstaclescene2d.entity

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Actor

open class ActorBase : Actor() {

    val circle = Circle(0f, 0f, 1f)
    lateinit var region: TextureRegion

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    override fun drawDebugBounds(shapes: ShapeRenderer) {
        if (!debug) return
        val oldColor = shapes.color.cpy()
        shapes.color = Color.RED
        shapes.x(circle.x, circle.y, 0.1f)
        shapes.circle(circle.x, circle.y, circle.radius, 24)
        shapes.color = oldColor
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

}