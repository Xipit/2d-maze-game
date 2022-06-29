package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.maze.game.MazeGame;
import com.maze.game.screens.LevelScreen;

/**
 * TODO: @Lucas add Docu krams
 *
 * @author  Lucas Neugebauer, Jörn Drechsler, Hanno Witzleb, Lilia Schneider, Simeon Baumann, Laurenz Oppelt
 */
public class StartMenuScreen extends ScreenAdapter {

    public StartMenuScreen() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.dispose();
        MazeGame.instance.setScreen(new LevelScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
