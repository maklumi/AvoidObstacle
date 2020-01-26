package com.sample

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.maklumi.util.GdxUtils
import com.sample.common.SampleBase
import com.sample.common.SampleInfo

class ShapeRendererSample : SampleBase() {
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private var drawGrid = true
    private var drawCircles = true
    private var drawRectangles = true
    private var drawPoints = true

    override fun create() {
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        Gdx.input.inputProcessor = this
    }

    override fun resize(width: Int, height: Int) { // NOTE: not centering camera
        viewport.update(width, height)
    }

    override fun render() {
        GdxUtils.clearScreen()
        renderer.projectionMatrix = camera.combined
        if (drawGrid) drawGrid()
        if (drawCircles) drawCircles()
        if (drawRectangles) drawRectangles()
        if (drawPoints) drawPoints()
    }

    private fun drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.WHITE
        val worldWidth = WORLD_WIDTH.toInt()
        val worldHeight = WORLD_HEIGHT.toInt()
        for (x in -worldWidth until worldHeight) {
            renderer.line(x.toFloat(), -worldHeight.toFloat(), x.toFloat(), worldHeight.toFloat())
        }
        for (y in -worldHeight until worldHeight) {
            renderer.line(-worldWidth.toFloat(), y.toFloat(), worldWidth.toFloat(), y.toFloat())
        }
        renderer.color = Color.RED
        renderer.line(-worldWidth.toFloat(), 0.0f, worldWidth.toFloat(), 0.0f)
        renderer.line(0.0f, -worldHeight.toFloat(), 0.0f, worldHeight.toFloat())
        renderer.end()
    }

    private fun drawCircles() {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.GREEN
        renderer.circle(2f, 2f, 2f, 30)
        renderer.circle(-5f, -5f, 1f)
        renderer.end()
    }

    private fun drawRectangles() {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.PURPLE
        renderer.rect(-8f, 4f, 4f, 2f)
        renderer.rect(-11f, 3f, 1f, 5f)
        renderer.end()
    }

    private fun drawPoints() {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.MAGENTA
        renderer.point(-5f, 0f, 0f)
        renderer.point(5f, -3f, 0f)
        renderer.point(8f, 6f, 1f)
        renderer.end()
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.x(-10f, 0f, 0.25f)
        renderer.end()
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.G -> drawGrid = !drawGrid
            Input.Keys.C -> drawCircles = !drawCircles
            Input.Keys.R -> drawRectangles = !drawRectangles
            Input.Keys.P -> drawPoints = !drawPoints
        }
        return true
    }

    override fun dispose() {
        renderer.dispose()
    }

    companion object {
        val SAMPLE_INFO = SampleInfo(ShapeRendererSample::class.java)
        private const val WORLD_HEIGHT = 21f
        private const val WORLD_WIDTH = 41f
    }
}