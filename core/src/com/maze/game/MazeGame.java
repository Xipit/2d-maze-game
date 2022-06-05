package com.maze.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class MazeGame extends Game {
	public static MazeGame instance;
	public final AssetManager assetManager = new AssetManager();

	public MazeGame() {
		MazeGame.instance = this;
	}
	@Override
	public void create () {
		setScreen(new LoadingScreen());
	}
}
