package com.obstacleashley.common

import com.badlogic.ashley.core.PooledEngine
import com.obstacleashley.Bounds
import ktx.ashley.entity

class EntityFactory(private val engine: PooledEngine) {

    // create entity and add to engine
    fun addPlayer() {
        engine.entity {
            with<Bounds> {
                bounds.set(1.5f, 3.2f, .4f)
            }
        }
    }


}