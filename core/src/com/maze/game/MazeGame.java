package com.maze.game;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {
	public static MazeGame instance;

	public MazeGame() {
		MazeGame.instance = this;
	}
	@Override
	public void create () {
		Asset asset = new Asset();
		asset.load();

		setScreen(new StartMenuScreen());
	}
}
