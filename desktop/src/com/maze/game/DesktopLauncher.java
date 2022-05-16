package com.maze.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.maze.game.MazeGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 480;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
		config.useVsync(true);
		config.setTitle("Drop Game");
		new Lwjgl3Application(new Drop(), config);
	}
}
