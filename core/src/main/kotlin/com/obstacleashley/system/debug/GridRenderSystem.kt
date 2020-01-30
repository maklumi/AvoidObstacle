package com.obstacleashley.system.debug

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.util.ViewportUtils

class GridRenderSystem(private val viewport: Viewport, private val renderer: ShapeRenderer) : EntitySystem() {

    override fun update(deltaTime: Float) {
        viewport.apply()
        ViewportUtils.drawGrid(viewport, renderer)
    }

}