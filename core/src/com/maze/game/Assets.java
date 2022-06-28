package com.maze.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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

    public static void loadAudio() {
        String directory = "assets/sound/";
        manager.load(directory + "collect_key.mp3", Sound.class);
        manager.load(directory + "cat_steps_on_trap.mp3", Sound.class);
        manager.load(directory + "open_door.mp3", Sound.class);
        manager.load(directory + "level_start_meow.mp3", Sound.class);
        manager.load(directory + "level_finished.mp3", Sound.class);
    }

    public static void loadTileMaps() {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.finishLoading();
    }

    public static void loadTileMap(String fileName){
        if(manager.isLoaded(fileName)) manager.unload(fileName);

        manager.load(fileName, TiledMap.class);
        manager.finishLoading();
    }

    public static void dispose() {
        // Textures are not disposed of during the session as the asset manager is supposed to maintain resources for the entire game session,
        // since all content is loaded into memory once and not per level.
        manager.dispose();
    }
}