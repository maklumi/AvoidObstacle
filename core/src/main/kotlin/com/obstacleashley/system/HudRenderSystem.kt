package com.obstacleashley.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH

class HudRenderSystem(
        private val viewport: Viewport,
        private val batch: SpriteBatch,
        private val font: BitmapFont
) : EntitySystem() {

    private val glyphLayout = GlyphLayout()

    override fun update(deltaTime: Float) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        drawHud()
        batch.end()
    }

    private fun drawHud() {
        glyphLayout.setText(font, "LIVES: ${GameManager.lives}")
        font.draw(batch, "LIVES: ${GameManager.lives}", 20f, HUD_HEIGHT - glyphLayout.height)
        glyphLayout.setText(font, "SCORES: ${GameManager.scores}")
        font.draw(batch, "SCORES: ${GameManager.scores}", HUD_WIDTH - glyphLayout.width - 20f, HUD_HEIGHT - glyphLayout.height)
    }
}