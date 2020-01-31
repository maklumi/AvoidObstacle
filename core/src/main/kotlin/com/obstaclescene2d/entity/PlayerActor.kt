package com.obstaclescene2d.entity

import com.obstacleavoid.config.PLAYER_SIZE

class PlayerActor : ActorBase() {

    init {
        circle.radius = PLAYER_SIZE/2
        region = null
    }

}