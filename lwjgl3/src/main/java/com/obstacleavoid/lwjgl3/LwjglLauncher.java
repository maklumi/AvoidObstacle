package com.obstacleavoid.lwjgl3;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.obstacleavoid.AvoidObstacle;
import com.obstacleavoid.config.GameConfig;

public class LwjglLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        return new LwjglApplication(new AvoidObstacle(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Avoid Obstacles";
        configuration.width = GameConfig.WIDTH;
        configuration.height = GameConfig.HEIGHT;
        return configuration;
    }
}