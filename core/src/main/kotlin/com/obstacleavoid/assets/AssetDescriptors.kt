@file:JvmName("AssetDescriptors")

package com.obstacleavoid.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont


val FONT = AssetDescriptor(UI_FONT, BitmapFont::class.java)
val PLAYER = AssetDescriptor("assets/gameplay/player.png", Texture::class.java)
val OBSTACLE = AssetDescriptor("assets/gameplay/obstacle.png", Texture::class.java)
val BACKGROUND = AssetDescriptor("assets/gameplay/background.png", Texture::class.java)
