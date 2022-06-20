package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameScreen implements Screen {

    SpriteBatch batch;
    Texture img;


    public GameScreen() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, Gdx.graphics.getWidth() - img.getWidth(), Gdx.graphics.getHeight() - img.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }
}
