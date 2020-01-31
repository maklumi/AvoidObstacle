package com.obstaclesprite.common

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Pool
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.RegionNames
import com.obstaclesprite.entity.ObstacleSprite
import com.obstaclesprite.entity.PlayerSprite

class EntityFactory(assetManager: AssetManager) {

    private val gamePlayAtlas = assetManager[GAME_PLAY]

    fun createPlayer(): PlayerSprite {
        val region = gamePlayAtlas.findRegion(RegionNames.PLAYER)
        return PlayerSprite(region)
    }

    private val obstacleRegion = gamePlayAtlas.findRegion(RegionNames.OBSTACLE)

    val obstaclePool: Pool<ObstacleSprite> = object : Pool<ObstacleSprite>(40) {
        override fun newObject(): ObstacleSprite {
            return ObstacleSprite(obstacleRegion)
        }
    }

}