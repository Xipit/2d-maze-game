package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.math.Vector3;

import java.awt.*;
import java.io.IOException;


public class MazeGame extends ApplicationAdapter {
	private OrthographicCamera camera;

	private Grid grid;

	private SpriteBatch sb;
	private Texture texture;
	private Sprite sprite;

	private Player player;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		try {
			grid = new Grid().load(1);
		} catch (IOException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}

		//create Spritebatch
		sb = new SpriteBatch();

		player = new Player();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		Map.renderer.setView(camera);
		Map.renderer.render();

		sb.setProjectionMatrix(camera.combined);

		// new batch sb
		sb.begin();
		sb.draw(Player.texture, Player.shape.x, Player.shape.y);
		sb.end();

		// Player movement by keystroke
		player.movement();
	}
	
	@Override
	public void dispose () {
		player.disposeTextures();
		sb.dispose();
	}
}
