package com.obstacleashley

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Circle

class Bounds(var bounds: Circle = Circle()) : Component

class PlayerTag : Component

class Movement(var xSpeed: Float = 0f, var ySpeed: Float = 0f) : Component