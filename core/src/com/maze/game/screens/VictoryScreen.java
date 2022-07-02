package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.maze.game.MazeGame;

/**
 * TODO: @Lucas add Docu krams
 *
 * @author  Lucas Neugebauer, Jörn Drechsler
 */
public class VictoryScreen extends ScreenAdapter {

    public VictoryScreen() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.dispose();

        //TODO add VictoryScreen
        MazeGame.instance.setScreen(new MenuScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
