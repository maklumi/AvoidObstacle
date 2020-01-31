package com.obstacleashley.system.collision

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Logger
import com.obstacleashley.ObstacleTag
import com.obstacleashley.PlayerTag
import com.obstacleashley.Position
import com.obstacleashley.common.OBSTACLE
import com.obstacleashley.common.boundsMapper
import ktx.ashley.allOf
import ktx.ashley.get

class CollisionSystem(private val listener: CollisionListener) : EntitySystem() {

    private val playerFamily = allOf(PlayerTag::class, Position::class).get()
    private val obstacleFamily = allOf(ObstacleTag::class, Position::class).get()

    override fun update(deltaTime: Float) {
        val players = engine.getEntitiesFor(playerFamily)
        val obstacles = engine.getEntitiesFor(obstacleFamily)

        for (player in players) {
            for (obstacle in obstacles) {
                val obstacleComp = obstacle[OBSTACLE]!!
                if (obstacleComp.isHit) continue
                if (isCollision(player, obstacle)) {
                    obstacleComp.isHit = true
                    log.debug("collision with obstacle")
                    listener.onCollision()
                }
            }
        }

    }

    private fun isCollision(player: Entity, obstacle: Entity): Boolean {
        val playerBoundC = player[boundsMapper]!!
        val obstacleBoundC = obstacle[boundsMapper]!!
        return Intersector.overlaps(playerBoundC.bounds, obstacleBoundC.bounds)
    }

    private val log = Logger(CollisionSystem::class.java.simpleName, Logger.DEBUG)

}