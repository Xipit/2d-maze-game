package com.maze.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
    public static AssetManager manager = new AssetManager();

    private Assets(){}

    public static void load() {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("prototyp_1.tmx", TiledMap.class);

        manager.load("prototyp_cat_32.png", Texture.class);

        manager.finishLoading();
    }

    public static void dispose() {
        manager.dispose();
    }
}