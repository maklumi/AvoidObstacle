package com.obstacleashley.system

import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.math.MathUtils
import com.obstacleashley.common.EntityFactory
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH

class ObstacleSpawnSystem(private val entityFactory: EntityFactory) : IntervalSystem(.5f) {

    override fun updateInterval() {
        val x = MathUtils.random(0f, WORLD_WIDTH)
        val y = WORLD_HEIGHT
        entityFactory.addObstacle(x, y)
    }

}