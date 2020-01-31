package com.obstacleashley.common

import com.badlogic.ashley.core.PooledEngine
import com.obstacleashley.*
import ktx.ashley.entity

class EntityFactory(private val engine: PooledEngine) {

    // create entity and add to engine
    fun addPlayer() {
        engine.entity {
            with<Bounds> {
                bounds.set(1.5f, 3.2f, .4f)
            }
            with<PlayerTag>()
            with<Movement>()
            with<Position> {
                x = 1.5f
                y = 3.2f
            }
            with<WorldWrapTag>()
        }
    }


    fun addObstacle(x: Float, y: Float) {
        engine.entity {
            with<Bounds> {
                bounds.set(x, y, 0.3f)
            }
            with<Movement> {
                this.ySpeed = -0.4f
            }
            with<Position> {
                this.x = x
                this.y = y
            }
            with<CleanUpTag>()
        }
    }

}