@file:JvmName("AssetDescriptors")

package com.obstacleavoid.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin


val FONT = AssetDescriptor(UI_FONT, BitmapFont::class.java)
val GAME_PLAY = AssetDescriptor(GAME_PLAY_ATLAS, TextureAtlas::class.java)

val UI_SKIN = AssetDescriptor<Skin>(UI_SKIN_PATH, Skin::class.java)

val HIT_SOUND = AssetDescriptor<Sound>(HIT_PATH, Sound::class.java)