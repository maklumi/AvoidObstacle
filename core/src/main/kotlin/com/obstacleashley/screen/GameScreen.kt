package com.obstacleashley.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleashley.common.EntityFactory
import com.obstacleashley.system.*
import com.obstacleashley.system.collision.CollisionListener
import com.obstacleashley.system.collision.CollisionSystem
import com.obstacleashley.system.debug.DebugCameraSystem
import com.obstacleashley.system.debug.DebugRenderSystem
import com.obstacleashley.system.debug.GridRenderSystem
import com.obstacleavoid.AvoidObstacle
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.HIT_SOUND
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH
import com.obstacleavoid.screen.MenuScreen
import com.obstacleavoid.util.GdxUtils

class GameScreen(private val game: AvoidObstacle) : Screen {

    private val log = Logger(GameScreen::class.java.name, Logger.DEBUG)

    private val assetManager = game.assetManager
    private lateinit var hudViewport: Viewport
    private lateinit var viewport: Viewport
    private val camera = OrthographicCamera()
    private lateinit var renderer: ShapeRenderer

    private val engine = PooledEngine()
    private val entityFactory = EntityFactory(engine)
    private var reset = false

    override fun show() {
        log.debug("show()")
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        hudViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT)
        val uiFont = assetManager[FONT]
        val hitSound = assetManager[HIT_SOUND]

        val listener = object : CollisionListener {
            override fun onCollision() {
                hitSound.play()
                GameManager.lives--
                if (GameManager.isGameOver) {
                    GameManager.highScore = GameManager.scores
                } else {
                    engine.removeAllEntities()
                    reset = true
                }
            }
        }

        val debugSystems = arrayListOf(
                GridRenderSystem(viewport, renderer),
                DebugCameraSystem(camera),
                DebugRenderSystem(viewport, renderer)
        )
        val systems = arrayListOf(
                PlayerSystem()
                , MovementSystem()
                , WorldWrapSystem(viewport)
                , BoundSystem()
                , ObstacleSpawnSystem(entityFactory)
                , CleanUpSystem()
                , CollisionSystem(listener)
                , ScoreSystem()
                , HudRenderSystem(hudViewport, game.batch, uiFont)
        )
        systems.addAll(debugSystems)
        systems.forEach { engine.addSystem(it) }

        entityFactory.addPlayer()
    }

    override fun render(delta: Float) {
        GdxUtils.clearScreen()
        engine.update(delta)

        if (reset) {
            entityFactory.addPlayer()
            reset = false
        }
        if (GameManager.isGameOver) {
            GameManager.reset()
            game.screen = MenuScreen(game)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        hudViewport.update(width, height, true)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}