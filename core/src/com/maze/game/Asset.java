package com.maze.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Asset {
    static AssetManager manager = new AssetManager();

    public void load() {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("prototyp_tilemap_64.tmx", TiledMap.class);

        manager.load("prototyp_cat.png", Texture.class);

        manager.finishLoading();
    }

    public void dispose() {
        manager.dispose();
    }
}
