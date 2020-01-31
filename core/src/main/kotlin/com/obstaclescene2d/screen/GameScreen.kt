package com.obstaclescene2d.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.badlogic.gdx.utils.viewport.FitViewport
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.HIT_SOUND
import com.obstacleavoid.assets.RegionNames
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.*
import com.obstacleavoid.screen.MenuScreen
import com.obstacleavoid.util.GdxUtils
import com.obstacleavoid.util.ViewportUtils
import com.obstacleavoid.util.debug.DebugCameraController
import com.obstaclescene2d.entity.ObstacleActor
import com.obstaclescene2d.entity.PlayerActor

class GameScreen(private val game: AvoidObstacle) : ScreenAdapter() {

    private val assetManager = game.assetManager
    private val batch = game.batch
    private val glyphLayout = GlyphLayout()

    private var camera = OrthographicCamera()
    private var viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val stage = Stage(viewport, batch)
    private var renderer = ShapeRenderer()

    private val uiCamera = OrthographicCamera()
    private val uiViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, uiCamera)
    private val uiFont = assetManager.get(FONT)

    private var lives = 3
    private var displayedScore = 0
    private val player = PlayerActor()
    private val startX = (WORLD_WIDTH - PLAYER_SIZE) / 2
    private val startY = PLAYER_SIZE / 2
    private val gameAtlas = assetManager[GAME_PLAY]
    private val playerRegion = gameAtlas.findRegion(RegionNames.PLAYER)
    private val obstacleRegion = gameAtlas.findRegion(RegionNames.OBSTACLE)
    private val backgroundRegion = gameAtlas.findRegion(RegionNames.BACKGROUND)
    private val background = Image(backgroundRegion)
    private val obstacles = Array<ObstacleActor>()
    private val obstaclePool = Pools.get(ObstacleActor::class.java)
    private var spawnTimer = 0f
    private var score = 0
    private var timer = 0f
    private val isGameOver: Boolean
        get() = lives == 0
    private val hitSound = game.assetManager[HIT_SOUND]

    override fun show() {
        player.setPosition(startX, startY)
        player.region = playerRegion
        stage.isDebugAll = true
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT)
        stage.addActor(background)
        stage.addActor(player)
        DebugCameraController.setStartPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
    }

    override fun render(delta: Float) {
        DebugCameraController.apply {
            handleDebugInput(delta)
            applyTo(camera)
        }

        GdxUtils.clearScreen()
        update(delta)
        updateScoreAndLives(delta)
        viewport.apply()
        renderGamePlay()

        uiViewport.apply()
        renderUi()

        viewport.apply()
        renderDebug()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        uiViewport.update(width, height, true)

        ViewportUtils.debugPixelPerUnit(viewport)
    }

    override fun dispose() {
        renderer.dispose()
    }

    private fun update(delta: Float) {
        if (isGameOver) {
            return
        }
        createNewObstacle(delta)
        removeObstacleOffScreen()
    }

    private fun createNewObstacle(delta: Float) {
        spawnTimer += delta
        if (spawnTimer > .5f) {
            val obstacle = obstaclePool.obtain()
            val x = MathUtils.random(0f, WORLD_WIDTH - OBSTACLE_SIZE)
            val y = WORLD_HEIGHT + OBSTACLE_SIZE
            obstacle.setPosition(x, y)
            obstacle.region = obstacleRegion
            obstacles.add(obstacle)
            stage.addActor(obstacle)
            spawnTimer = 0f
        }
    }

    private fun removeObstacleOffScreen() {
        val iterable = Array.ArrayIterable<ObstacleActor>(obstacles)
        for (obstacle in iterable) {
            if (obstacle.y < -OBSTACLE_SIZE) {
                obstacles.removeValue(obstacle, true)
                obstaclePool.free(obstacle)
                stage.actors.removeValue(obstacle, true)
            }
        }
    }

    private fun renderGamePlay() {
        batch.projectionMatrix = camera.combined
        batch.begin()

        drawGamePlay()

        batch.end()

        stage.act()
        stage.draw()
    }

    private fun drawGamePlay() {

    }

    private fun renderUi() {
        batch.projectionMatrix = uiCamera.combined
        batch.begin()

        val textLives = "LIVES: $lives"
        glyphLayout.setText(uiFont, textLives)
        uiFont.draw(batch, textLives, 20f, HUD_HEIGHT - glyphLayout.height)

        val textScore = "SCORE: $displayedScore"
        glyphLayout.setText(uiFont, textScore)
        uiFont.draw(batch, textScore, HUD_WIDTH - glyphLayout.width - 20f, HUD_HEIGHT - glyphLayout.height)

        batch.end()
    }

    private fun renderDebug() {
        ViewportUtils.drawGrid(viewport, renderer)
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
            if (!isGameOver) {
                restart()
            } else {
                GameManager.highScore = score
                game.screen = MenuScreen(game)
            }
        }
    }

    private fun smoothOutScoreDisplay(delta: Float) {
        if (displayedScore < score) {
//            displayedScore = Math.min(score, displayedScore + (delta * 60).toInt()) // is same as below
            displayedScore = score.coerceAtMost(displayedScore + (delta * 60).toInt())
        }
    }

    private fun isCollision(): Boolean {
        var isOverlap = false
        val iterable = Array.ArrayIterable<ObstacleActor>(obstacles)
        for (obstacle in iterable) {
            if (obstacle.isAlreadyHit) continue
            isOverlap = Intersector.overlaps(player.circle, obstacle.circle)
            if (isOverlap) {
                hitSound.play()
                obstacle.isAlreadyHit = true
                return true
            }
        }
        return isOverlap
    }

    private fun restart() {
        val iterable = Array.ArrayIterable<ObstacleActor>(obstacles)
        for (obstacle in iterable) {
            // remove from stage
            obstacle.remove()
            // return to pool
            obstaclePool.free(obstacle)
        }
        obstacles.clear()
        player.setPosition(startX, startY)
    }

}