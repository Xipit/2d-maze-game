package com.maze.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MazeGame extends Game {
	public static MazeGame instance;
	public final AssetManager assetManager = new AssetManager();

	public MazeGame() {
		MazeGame.instance = this;
	}
	@Override
	public void create () {
		// Lade assets mit
		MazeGame.instance.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
		MazeGame.instance.assetManager.load("prototyp_tilemap_64.tmx", TiledMap.class);

		MazeGame.instance.assetManager.load("prototyp_cat.png", Texture.class);

		MazeGame.instance.assetManager.finishLoading();
		setScreen(new StartMenuScreen());
	}
}
