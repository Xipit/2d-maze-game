package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;


public class MazeGame extends ApplicationAdapter {
	private OrthographicCamera camera;

	private Grid grid;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		try {
			grid = new Grid().load(1);
		} catch (IOException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();

		Map.renderer.setView(camera);
		Map.renderer.render();
	}
	
	@Override
	public void dispose () {
	}
}
