package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MazeGame extends ApplicationAdapter {
	private Texture pathWood;
	private Texture wallStone;

	private OrthographicCamera camera;

	private SpriteBatch batch;

	private Grid grid;

	@Override
	public void create () {
		pathWood = new Texture(Gdx.files.internal("oldwood_DIFF_128.png"));
		wallStone = new Texture(Gdx.files.internal("Rock_03_DIFF_128.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch = new SpriteBatch();

		try {
			grid = new Grid().load(1);
		} catch (java.io.IOException | IllegalArgumentException e) {
			;  // todo exception handling
		}
		grid.printGrid();  // testweise
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();

		Map.renderer.setView(camera);
		Map.renderer.render();

		/*
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < grid.getSizeX(); i++) {
			for (int j = 0; j < grid.getSizeY(); j++) {
				// todo Skalierung der Texturen bezüglich der Fenstergröße, Anordnung
				//  ist Rechteck "nutzbarer" Fläche, Außenwand und Türen müssen noch außen herum angelegt werden
				if (grid.getValue(i, j) == '0') {
					batch.draw(wallStone,0,0);
				}
				else {
					batch.draw(pathWood,0,0);
				}
			}
		}
		batch.end();
		*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
