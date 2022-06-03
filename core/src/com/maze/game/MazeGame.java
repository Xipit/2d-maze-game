package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
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

	private Texture katzeImage;

	private Rectangle katze;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		try {
			grid = new Grid().load(1);
		} catch (IOException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}

		//load images Katze_bearbeitet.png
		katzeImage = new Texture(Gdx.files.internal("Katze_bearbeitet.png"));

		//create Spritebatch
		sb = new SpriteBatch();

		//// create a Rectangle
		katze = new Rectangle();
		katze.x = 800 / 2 - 64 / 2;
		katze.y = 20;
		katze.width = 64;
		katze.height = 64;
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
		sb.draw(katzeImage, katze.x, katze.y);
		sb.end();

		// Player movement by mouse
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			katze.x = (int) (touchPos.x - 64/2);
			katze.y = (int) (touchPos.y - 64/2);
		}

		// Player movement by keystroke
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) katze.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) katze.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) katze.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) katze.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) katze.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) katze.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.W)) katze.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) katze.y -= 200 * Gdx.graphics.getDeltaTime();

		// Player boundaries
		if(katze.x < 0) katze.x = 0;
		if(katze.x > Gdx.graphics.getWidth() - 64) katze.x =  Gdx.graphics.getWidth() - 64;
		if(katze.y < 0) katze.y = 0;
		if(katze.y > Gdx.graphics.getHeight() - 64) katze.y =  Gdx.graphics.getHeight() - 64;

	}
	
	@Override
	public void dispose () {
		katzeImage.dispose();
		sb.dispose();
	}
}
