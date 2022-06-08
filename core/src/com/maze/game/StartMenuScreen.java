package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class StartMenuScreen extends ScreenAdapter {
    static String[] tilemaps = {"prototyp_tilemap_64.tmx"};
    public StartMenuScreen() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        MazeGame.instance.setScreen(new LevelScreen(tilemaps[0]));
    }
}
