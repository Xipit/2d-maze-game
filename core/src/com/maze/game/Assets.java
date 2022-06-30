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

    public static final String CAT_DIAGONAL_LEFT_FOOT =     "assets/character/Cat_diagonal_leftFoot.png";
    public static final String CAT_DIAGONAL_MIDDLE =        "assets/character/Cat_diagonal_middle.png";
    public static final String CAT_DIAGONAL_RIGHT_FOOT =    "assets/character/Cat_diagonal_rightFoot.png";
    public static final String CAT_LEFT_FOOT =              "assets/character/Cat_leftFoot.png";
    public static final String CAT_MIDDLE =                 "assets/character/Cat_middle.png";
    public static final String CAT_RIGHT_FOOT =             "assets/character/Cat_rightFoot.png";
    public static final String CAT_SITTING =                "assets/character/Cat_sitting.png";
            ;

    public static void loadTextures() {
        manager.load(CAT_DIAGONAL_LEFT_FOOT, Texture.class);
        manager.load(CAT_DIAGONAL_MIDDLE, Texture.class);
        manager.load(CAT_DIAGONAL_RIGHT_FOOT, Texture.class);
        manager.load(CAT_LEFT_FOOT, Texture.class);
        manager.load(CAT_MIDDLE, Texture.class);
        manager.load(CAT_RIGHT_FOOT, Texture.class);
        manager.load(CAT_SITTING, Texture.class);
        manager.finishLoading();
    }

    public static final String COLLECT_KEY_SOUND_FILENAME =     "assets/sound/collect_key.mp3";
    public static final String STEP_ON_TRAP_SOUND_FILENAME =    "assets/sound/cat_steps_on_trap.mp3";
    public static final String OPEN_DOOR_SOUND_FILENAME =       "assets/sound/open_door.mp3";
    public static final String LEVEL_START_SOUND_FILENAME =     "assets/sound/level_start_meow.mp3";
    public static final String LEVEL_FINISHED_SOUND_FILENAME =  "assets/sound/level_finished.mp3";

    public static void loadAudio() {

        manager.load(COLLECT_KEY_SOUND_FILENAME, Sound.class);
        manager.load(STEP_ON_TRAP_SOUND_FILENAME, Sound.class);
        manager.load(OPEN_DOOR_SOUND_FILENAME, Sound.class);
        manager.load(LEVEL_FINISHED_SOUND_FILENAME, Sound.class);
        manager.load(LEVEL_START_SOUND_FILENAME, Sound.class);
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