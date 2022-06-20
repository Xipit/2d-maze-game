package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import java.util.HashMap;
import java.util.Map;

public class StartMenuScreen extends ScreenAdapter {
    static Map<Integer, String> tilemaps = new HashMap<Integer, String>() {{
        put(1, "prototyp_tilemap_64.tmx");
    }};
    public StartMenuScreen() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        MazeGame.instance.setScreen(new LevelScreen(tilemaps.get(1)));
    }
}
