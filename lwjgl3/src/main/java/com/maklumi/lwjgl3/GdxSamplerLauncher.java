package com.maklumi.lwjgl3;

import javax.swing.*;
import java.awt.*;

import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class GdxSamplerLauncher extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int CELL_WIDTH = 200;
    private static final int CANVAS_WIDTH = WIDTH - CELL_WIDTH;

    // enables us to embed libgdx app/game into java desktop app
    private LwjglAWTCanvas lwjglAWTCanvas;

    public GdxSamplerLauncher() throws HeadlessException {
        setTitle(GdxSamplerLauncher.class.getSimpleName());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // tell window (jframe) to resize and layout our components
        pack();
        setVisible(true);

        launchSample("com.sample.InputPollingSample");
    }

    // == main ==
    public static void main(String[] args) {
        // must be used to run our jframe properly
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GdxSamplerLauncher();
            }
        });
    }

    private void launchSample(String name) {
        System.out.println("launching sample name= " + name);

        Container container = getContentPane();

        if (lwjglAWTCanvas != null) {
            lwjglAWTCanvas.stop();
            container.remove(lwjglAWTCanvas.getCanvas());
        }

        ApplicationListener sample = null;

        try {
            Class<ApplicationListener> clazz = ClassReflection.forName(name);
            sample = ClassReflection.newInstance(clazz);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        lwjglAWTCanvas = new LwjglAWTCanvas(sample);
        lwjglAWTCanvas.getCanvas().setSize(CANVAS_WIDTH, HEIGHT);
        container.add(lwjglAWTCanvas.getCanvas(), BorderLayout.CENTER);

        pack();
    }
}
