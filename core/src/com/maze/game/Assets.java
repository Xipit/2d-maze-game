package com.maze.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * <h1>Assetmanager</h1>
 * Statische Klasse (soweit es Java erlaubt) zum zentralen Managen aller möglichen Assets und deren Aufrufe.
 *
 * @author  Hanno Witzleb, Jörn Drechsler
 */
public class Assets {
    public static AssetManager manager = new AssetManager();

    private Assets(){}

    public static void loadTextures() {
        String directory = "assets/sound/";
        manager.load("prototyp_cat_32.png", Texture.class);
        manager.finishLoading();
    }

    public static void loadAudio() {
        String directory = "assets/sound/";
        manager.load(Audio.COLLECT_KEY_SOUND_FILENAME, Sound.class);
        manager.load(Audio.STEP_ON_TRAP_SOUND_FILENAME, Sound.class);
        manager.load(Audio.OPEN_DOOR_SOUND_FILENAME, Sound.class);
        manager.load(Audio.LEVEL_FINISHED_SOUND_FILENAME, Sound.class);
        manager.load(Audio.LEVEL_START_SOUND_FILENAME, Sound.class);
        manager.finishLoading();
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