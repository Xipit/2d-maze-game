package com.maze.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.maze.game.MazeGame;

/**
 * <h1>Desktop spezifischer Softwareschnittstelle</h1>
 * Von libgdx vorgesehene plattformspezifische Schnittstelle der Software.
 *
 * @author  Jörn Drechsler, Hanno Witzleb, Lilia Schneider, Lucas Neugebauer, Simeon Baumann, Laurenz Oppelt
 */

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Maze Game");
		config.setWindowIcon("assets/icon/Cat_sitting.png");
		config.setWindowedMode(MazeGame.SCREEN_WIDTH, MazeGame.SCREEN_HEIGHT);
		config.setResizable(false);
		new Lwjgl3Application(new MazeGame(), config);
	}
}
