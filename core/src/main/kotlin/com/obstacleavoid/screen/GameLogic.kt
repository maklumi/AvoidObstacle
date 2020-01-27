package com.obstacleavoid.screen

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.obstacleavoid.config.Difficulty
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.entity.Obstacle
import com.obstacleavoid.entity.Player
import com.obstacleavoid.util.debug.DebugCameraController

class GameLogic {
    val player = Player()
    val obstacles = Array<Obstacle>()
    private var spawnTimer = 0f
    private var lives = 3
    val textLives: String
        get() = "LIVES: $lives"
    private var score = 0
    private var displayedScore = 0
    private var timer = 0f
    val textScore: String
        get() = "SCORE: $displayedScore"
    private var level = Difficulty.EASY
    private val obstaclePool = Pools.get(Obstacle::class.java, 40) // max 40 obstacles

    val isGameOver: Boolean
        get() = lives == 0

    init {
        player.setPosition(1.5f, 3.2f)
        DebugCameraController.setStartPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
    }

    fun update(delta: Float) {
        if (isGameOver) return
        updateObstacles(delta)
        updateScoreAndLives(delta)
    }

    private fun updateScoreAndLives(delta: Float) {
        timer += delta
        if (timer > 2f) {
            timer = 0f
            score += MathUtils.random(10, 30)
        }

        smoothOutScoreDisplay(delta)

        if (isCollision()) {
            lives--
            if (!isGameOver) restart()
        }
    }

    private fun restart() {
        obstaclePool.freeAll(obstacles)
        obstacles.clear()
        player.setPosition(1.5f, 3.2f)
    }

    private fun smoothOutScoreDisplay(delta: Float) {
        if (displayedScore < score) {
//            displayedScore = Math.min(score, displayedScore + (delta * 60).toInt()) // is same as below
            displayedScore = score.coerceAtMost(displayedScore + (delta * 60).toInt())
        }
    }

    private fun isCollision(): Boolean {
        var isOverlap = false
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            if (obstacle.isAlreadyHit) continue
            isOverlap = Intersector.overlaps(player.bounds, obstacle.bounds)
            if (isOverlap) {
                obstacle.isAlreadyHit = true
                return true
            }
        }
        return isOverlap
    }

    private fun updateObstacles(delta: Float) {
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            obstacle.update(level.speed)
        }
        createNewObstacle(delta)
        removeObstacleOffScreen()
    }

    private fun createNewObstacle(delta: Float) {
        spawnTimer += delta
        if (spawnTimer > .5f) {
//            val obstacle = Obstacle()
            val obstacle = obstaclePool.obtain()
            val x = MathUtils.random(obstacle.radius, WORLD_WIDTH - obstacle.radius)
            val y = WORLD_HEIGHT + obstacle.radius * 2
            obstacle.setPosition(x, y)
            obstacles.add(obstacle)
            spawnTimer = 0f
        }
    }

    private fun removeObstacleOffScreen() {
        val iterable = Array.ArrayIterable<Obstacle>(obstacles)
        for (obstacle in iterable) {
            if (obstacle.y < -3 * obstacle.radius) {
                obstacles.removeValue(obstacle, true)
                obstaclePool.free(obstacle) // free from pool
            }
        }
    }

}