package com.maze.game;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {
	public static MazeGame instance;

	public MazeGame() {
		MazeGame.instance = this;
	}
	@Override
	public void create () {
		Assets.loadTextures();
		Assets.loadTileMaps();

		setScreen(new StartMenuScreen());
	}

	@Override
	public void dispose(){
		super.dispose();
		Assets.dispose();
		Audio.dispose();
	}
}
