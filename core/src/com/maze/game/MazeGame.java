package com.maze.game;

import com.badlogic.gdx.Game;
import com.maze.game.screens.MenuScreen;
import com.maze.game.screens.StartMenuScreen;

/**
 * <h1>Zentrale Spielinstanz</h1>
 * Von libgdx vorgesehene plattformübergreifende Schnittstelle der Software.
 *
 * @author  Jörn Drechsler, Hanno Witzleb, Lucas Neugebauer, Lilia Schneider, Simeon Baumann, Laurenz Oppelt
 */
public class MazeGame extends Game {
	public static MazeGame instance;

	public static final int SCREEN_WIDTH = 1080;
	public static final int SCREEN_HEIGHT = 720;

	public MazeGame() {
		MazeGame.instance = this;
	}
	@Override
	public void create () {
		Assets.loadTextures();
		Assets.loadAudio();
		Assets.loadTileMaps();

		setScreen(new MenuScreen());
	}

	@Override
	public void dispose(){
		super.dispose();
		Assets.dispose();
	}
}
