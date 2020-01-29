package com.sample.ashley

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture

class PositionComponent : Component {
    var x: Float = 0f
    var y: Float = 0f
}

class SizeComponent : Component {
    var width: Float = 0f
    var height: Float = 0f
}

class TextureComponent : Component {
    var texture: Texture? = null
}