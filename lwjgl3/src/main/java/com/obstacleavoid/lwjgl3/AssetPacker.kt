@file:JvmName("AssetPacker")

package com.obstacleavoid.lwjgl3

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPacker {

    private const val DRAW_DEBUG_OUTLINE = false
    private const val INPUT = "lwjgl3/assets-raw/ui"
    private const val OUTPUT = "assets/ui"
    private const val ATLAS_NAME = "ui"

    @JvmStatic
    fun main(args: Array<String>) {
        val settings = TexturePacker.Settings()
        settings.debug = DRAW_DEBUG_OUTLINE
        settings.maxWidth = 2048
        settings.maxHeight = 2048

        TexturePacker.process(settings, INPUT, OUTPUT, ATLAS_NAME)
    }
}