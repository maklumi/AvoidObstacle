package com.maklumi.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.maklumi.config.GameConfig;
import com.sample.ApplicationListenerSample;
import com.sample.InputPollingSample;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class LwjglLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        return new LwjglApplication(new InputPollingSample(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Learning LibGdx";
        configuration.width = GameConfig.WIDTH;
        configuration.height = GameConfig.HEIGHT;
        return configuration;
    }
}