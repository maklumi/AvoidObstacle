package com.obstacleashley.common

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.obstacleashley.*
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames.BACKGROUND
import com.obstacleavoid.assets.RegionNames.PLAYER
import com.obstacleavoid.assets.RegionNames.OBSTACLE
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.OBSTACLE_SIZE
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import ktx.ashley.entity

class EntityFactory(private val engine: PooledEngine, assetManager: AssetManager) {

    private val gameAtlas = assetManager[GAME_PLAY]

    // create entity and add to engine
    fun addPlayer() {
        val playerSize = com.obstacleavoid.config.PLAYER_SIZE
        val startX = (WORLD_WIDTH - playerSize) / 2
        val startY = 1 - playerSize / 2
        engine.entity {
            with<Bounds> {
                bounds.set(startX, startY, playerSize / 2)
            }
            with<PlayerTag>()
            with<Movement>()
            with<Position> {
                x = startX
                y = startY
            }
            with<WorldWrapTag>()
            with<Texture> {
                region = gameAtlas.findRegion(PLAYER)
            }
            with<Dimension> {
                width = playerSize
                height = playerSize
            }
        }
    }


    fun addObstacle(x: Float, y: Float) {
        engine.entity {
            with<Bounds> {
                bounds.set(x, y, OBSTACLE_SIZE / 2)
            }
            with<Movement> {
                ySpeed = -GameManager.difficultyLevel.speed
            }
            with<Position> {
                this.x = x
                this.y = y
            }
            with<CleanUpTag>()
            with<ObstacleTag>()
            with<Texture> {
                region = gameAtlas.findRegion(OBSTACLE)
            }
            with<Dimension> {
                width = OBSTACLE_SIZE
                height = OBSTACLE_SIZE
            }
        }
    }

    fun addBackground() {
        engine.entity {
            with<Texture> {
                region = gameAtlas.findRegion(BACKGROUND)
            }
            with<Position> {
                x = 0f
                y = 0f
            }
            with<Dimension> {
                width = WORLD_WIDTH
                height = WORLD_HEIGHT
            }
        }
    }
}