package com.maze.game;

import com.badlogic.gdx.Game;
import com.maze.game.screens.MenuScreen;

/**
 * <h1>Zentrale Spielinstanz</h1>
 * Von libgdx vorgesehene plattformübergreifende Schnittstelle der Software.
 *
 * @author  Jörn Drechsler, Hanno Witzleb, Lucas Neugebauer, Lilia Schneider, Simeon Baumann, Laurenz Oppelt
 */
public class MazeGame extends Game {
	public static MazeGame instance;

	public static final int DEFAULT_SCREEN_WIDTH = 1080;
	public static final int DEFAULT_SCREEN_HEIGHT = 720;
	public static int SCREEN_WIDTH = DEFAULT_SCREEN_WIDTH;
	public static int SCREEN_HEIGHT = DEFAULT_SCREEN_HEIGHT;


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
