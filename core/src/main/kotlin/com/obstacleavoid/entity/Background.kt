package com.obstacleavoid.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.obstacleavoid.config.WORLD_HEIGHT
import com.obstacleavoid.config.WORLD_WIDTH

class Background {

    val texture = Texture(Gdx.files.internal("assets/gameplay/background.png"))

    val width = WORLD_WIDTH

    val height = WORLD_HEIGHT

    val x = 0f

    val y = 0f
}