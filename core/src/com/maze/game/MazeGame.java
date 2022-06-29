package com.maze.game;

import com.badlogic.gdx.Game;
import com.maze.game.screens.StartMenuScreen;

/**
 * <h1>Zentrale Spielinstanz</h1>
 * Von libgdx vorgesehene plattformübergreifende Schnittstelle der Software.
 *
 * @author  Jörn Drechsler, Hanno Witzleb, Lucas Neugebauer, Lilia Schneider, Simeon Baumann, Laurenz Oppelt
 */
public class MazeGame extends Game {
	public static MazeGame instance;

	public MazeGame() {
		MazeGame.instance = this;
	}
	@Override
	public void create () {
		Assets.loadTextures();
		Assets.loadAudio();
		Assets.loadTileMaps();

		setScreen(new StartMenuScreen());
	}

	@Override
	public void dispose(){
		super.dispose();
		Assets.dispose();
	}
}
