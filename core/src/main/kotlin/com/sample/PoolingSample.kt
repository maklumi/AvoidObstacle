package com.sample

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.Pool.Poolable
import com.sample.common.SampleBase
import com.sample.common.SampleInfo


class PoolingSample : SampleBase() {
    private val log: Logger = Logger(PoolingSample::class.java.name, Logger.DEBUG)

    companion object {
        val SAMPLE_INFO = SampleInfo(PoolingSample::class.java)
    }

    private val bulletSpawnTime = 1f

    private val bullets: Array<Bullet> = Array()
    private var timer = 0f

    private class Bullet : Poolable {
        private val bulletAliveTime = 3f

        var alive = true
        var timer = 0f
        fun update(delta: Float) {
            timer += delta
            if (alive && timer > bulletAliveTime) {
                alive = false
            }
        }

        override fun reset() {
            alive = true
            timer = 0f
        }
    }

    private val bulletPool: Pool<Bullet> = object : Pool<Bullet>() {
        override fun newObject(): Bullet? {
            log.debug("newObject")
            return Bullet()
        }

        override fun free(bullet: Bullet) {
            log.debug("before free object= $bullet free= $free")
            super.free(bullet)
            log.debug("after free object= $bullet free= $free")
        }

        override fun obtain(): Bullet? {
            log.debug("before obtain free= $free")
            val ret: Bullet = super.obtain()
            log.debug("after obtain free= $free")
            return ret
        }

        override fun reset(bullet: Bullet) {
            log.debug("resetting object= $bullet")
            super.reset(bullet)
        }
    }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime
        timer += delta
        if (timer > bulletSpawnTime) {
            timer = 0f
            val bullet = bulletPool.obtain()
            bullets.add(bullet)
            log.debug("create alive bullets= " + bullets.size)
        }
        val iterable = Array.ArrayIterable<Bullet>(bullets)
        for (bullet in iterable) {
            bullet.update(delta)
            if (!bullet.alive) {
                bullets.removeValue(bullet, true)
                bulletPool.free(bullet)
                log.debug("after free alive bullets= " + bullets.size)
            }
        }
    }

    override fun dispose() {
        bulletPool.freeAll(bullets)
        bulletPool.clear()
        bullets.clear()
    }
}