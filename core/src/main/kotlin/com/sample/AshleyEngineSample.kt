package com.sample

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class AshleyEngineSample : SampleBase() {

    companion object {
        private val log = Logger(AshleyEngineSample::class.java.name, Logger.DEBUG)

        val SAMPLE_INFO = SampleInfo(AshleyEngineSample::class.java)

        private const val SPAWN_TIME = 1f   // spawn 1 bullet every 1 second
        private const val REMOVE_TIME = 3f  // then remove 1 bullet every 3 seconds
    }

    private lateinit var engine: Engine

    private val bullets: Array<Entity> = Array()

    private var spawnTimer = 0f
    private var removeTimer = 0f

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        engine = Engine()
        engine.addEntityListener(object : EntityListener {
            override fun entityAdded(entity: Entity) {
                log.debug("entityAdded= $entity")
                log.debug("total entities= " + engine.entities.size())
            }

            override fun entityRemoved(entity: Entity) {
                log.debug("entityRemoved= $entity")
                log.debug("total entities= " + engine.entities.size())
            }
        })
        addBullet()
    }

    private fun addBullet() {
        val bullet = Entity()
        bullets.add(bullet)
        engine.addEntity(bullet)
    }

    override fun render() {
        GdxUtils.clearScreen()
        val delta = Gdx.graphics.deltaTime
        engine.update(delta)
        spawnTimer += delta
        if (spawnTimer > SPAWN_TIME) {
            spawnTimer = 0f
            addBullet()
        }
        removeTimer += delta
        if (removeTimer > REMOVE_TIME) {
            removeTimer = 0f
            if (bullets.size > 0) {
                val bullet = bullets.first()
                bullets.removeValue(bullet, true)
                engine.removeEntity(bullet)
            }
        }
    }
}