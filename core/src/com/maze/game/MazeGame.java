package com.maze.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.text.html.HTMLDocument;

public class MazeGame extends Game {

	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}
}
