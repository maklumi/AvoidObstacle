package com.obstacleashley.system.debug

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.util.debug.DebugCameraController

class DebugCameraSystem(private val camera: OrthographicCamera) : EntitySystem() {

    init {
        DebugCameraController.setStartPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
    }

    override fun update(deltaTime: Float) {
        DebugCameraController.handleDebugInput(deltaTime)
        DebugCameraController.applyTo(camera)
    }

}