package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LoadingScreen extends ScreenAdapter {
    public LoadingScreen() {
        MazeGame.instance.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        MazeGame.instance.assetManager.load("prototyp_tilemap.tmx", TiledMap.class);

        // MazeGame.instance.assetManager.load("Katze_bearbeitet.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        if (MazeGame.instance.assetManager.update())
            // todo Startmenü
            MazeGame.instance.setScreen(new LevelScreen());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}
