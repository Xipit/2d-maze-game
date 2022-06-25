package com.maze.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
    public static AssetManager manager = new AssetManager();

    private Assets(){}

    public static void loadTextures() {
        manager.load("prototyp_cat_32.png", Texture.class);
        manager.finishLoading();
    }

    public static void loadTileMaps() {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("prototyp_1.tmx", TiledMap.class);
        manager.finishLoading();
    }

    public static void dispose() {
        // Textures are not disposed of during the session as the asset manager is supposed to maintain resources for the entire game session,
        // since all content is loaded into memory once and not per level.
        manager.dispose();
    }
}