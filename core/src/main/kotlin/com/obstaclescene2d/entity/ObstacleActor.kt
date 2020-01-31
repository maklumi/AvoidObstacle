package com.obstaclescene2d.entity

import com.badlogic.gdx.utils.Pool
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.OBSTACLE_SIZE

class ObstacleActor : ActorBase(), Pool.Poolable {

    var isAlreadyHit = false
    private val ySpeed = GameManager.difficultyLevel.speed

    init {
        circle.radius = OBSTACLE_SIZE / 2
        setSize(OBSTACLE_SIZE, OBSTACLE_SIZE)
    }

    override fun act(delta: Float) {
        super.act(delta)
        update()
    }

    fun update() {
        y -= ySpeed
    }

    override fun reset() {
        isAlreadyHit = false
    }

}