package com.obstacleashley.common

import com.obstacleashley.Bounds
import com.obstacleashley.Movement
import ktx.ashley.mapperFor

val boundsMapper = mapperFor<Bounds>()
val MOVEMENT = mapperFor<Movement>()
val POSITION = mapperFor<com.obstacleashley.Position>()
val OBSTACLE = mapperFor<com.obstacleashley.ObstacleTag>()
val TEXTURE = mapperFor<com.obstacleashley.Texture>()
val DIMENSION = mapperFor<com.obstacleashley.Dimension>()