package com.maze.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.maze.game.MazeGame;
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Maze Game");
		config.setWindowSizeLimits(1080, 720, 1080, 720);
		new Lwjgl3Application (new MazeGame(), config);
	}
}
