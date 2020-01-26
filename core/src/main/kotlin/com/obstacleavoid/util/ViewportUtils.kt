@file:JvmName("ViewportUtils")

package com.obstacleavoid.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.Viewport

object ViewportUtils {
    private val log: Logger = Logger("ViewportUtils", Logger.DEBUG)

    private const val DEFAULT_CELL_SIZE = 1

    fun drawGrid(viewport: Viewport, renderer: ShapeRenderer, sizeOfCell: Int = DEFAULT_CELL_SIZE) {
        val cellSize = if (sizeOfCell < DEFAULT_CELL_SIZE) sizeOfCell else DEFAULT_CELL_SIZE

        // copy old color from render
        val oldColor = Color(renderer.color)
        val worldWidth = viewport.worldWidth.toInt()
        val worldHeight = viewport.worldHeight.toInt()
        val doubleWorldWidth = worldWidth * 2
        val doubleWorldHeight = worldHeight * 2
        renderer.projectionMatrix = viewport.camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.WHITE
        // draw vertical lines
        for (x in -doubleWorldWidth until doubleWorldWidth step cellSize) {
            renderer.line(x.toFloat(), -doubleWorldHeight.toFloat(), x.toFloat(), doubleWorldHeight.toFloat())
        }

        // draw horizontal lines
        var y = -doubleWorldHeight
        while (y < doubleWorldHeight) {
            renderer.line(-doubleWorldWidth.toFloat(), y.toFloat(), doubleWorldWidth.toFloat(), y.toFloat())
            y += cellSize
        }
        // draw x-y axis lines
        renderer.color = Color.RED
        renderer.line(0f, -doubleWorldHeight.toFloat(), 0f, doubleWorldHeight.toFloat())
        renderer.line(-doubleWorldWidth.toFloat(), 0f, doubleWorldWidth.toFloat(), 0f)
        // draw world bounds
        renderer.color = Color.GREEN
        renderer.line(0f, worldHeight.toFloat(), worldWidth.toFloat(), worldHeight.toFloat())
        renderer.line(worldWidth.toFloat(), 0f, worldWidth.toFloat(), worldHeight.toFloat())
        renderer.end()
        renderer.color = oldColor
    }

    fun debugPixelPerUnit(viewport: Viewport) {
        val screenWidth = viewport.screenWidth.toFloat()
        val screenHeight = viewport.screenHeight.toFloat()
        val worldWidth = viewport.worldWidth
        val worldHeight = viewport.worldHeight
        // PPU => pixels per world unit
        val xPPU = screenWidth / worldWidth
        val yPPU = screenHeight / worldHeight
        log.debug("x PPU= $xPPU yPPU= $yPPU")
    }

}