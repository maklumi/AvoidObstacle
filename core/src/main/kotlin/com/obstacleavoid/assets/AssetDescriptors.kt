@file:JvmName("AssetDescriptors")

package com.obstacleavoid.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas


val FONT = AssetDescriptor(UI_FONT, BitmapFont::class.java)
val GAME_PLAY = AssetDescriptor(GAME_PLAY_ATLAS, TextureAtlas::class.java)
