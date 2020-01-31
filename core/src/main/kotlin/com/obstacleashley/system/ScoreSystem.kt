package com.obstacleashley.system

import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.math.MathUtils
import com.obstacleavoid.common.GameManager

class ScoreSystem : IntervalSystem(2f) {

    override fun updateInterval() {
        GameManager.scores += MathUtils.random(10, 30)
    }

}