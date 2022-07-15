package com.maze.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.maze.game.levels.*;

/**
 * <h1>Assetmanager</h1>
 * Statische Klasse (soweit es Java erlaubt) zum zentralen Managen aller möglichen Assets und deren Aufrufe.
 *
 * @author  Hanno Witzleb, Jörn Drechsler
 */
public class Assets {
    public static AssetManager manager = new AssetManager();

    private Assets(){}

    public static final String VICTORY_IMAGE =          "menu/victoryScreen.png";
    public static final String NEXT_LEVEL =             "menu/nextlvl_aus.png";
    public static final String NEXT_LEVEL_PRESSED =     "menu/nextlvl_an.png";
    public static final String RELOAD_LEVEL =           "menu/repeat_aus.png";
    public static final String RELOAD_LEVEL_PRESSED =   "menu/repeat_an.png";

    public static void loadVictoryMenuTextures(){
        manager.load(VICTORY_IMAGE, Texture.class);
        manager.load(NEXT_LEVEL, Texture.class);
        manager.load(NEXT_LEVEL_PRESSED, Texture.class);
        manager.load(LEVELS_BACKWARD, Texture.class);
        manager.load(RELOAD_LEVEL, Texture.class);
        manager.load(RELOAD_LEVEL_PRESSED, Texture.class);
        manager.finishLoading();
    }

    // determines Appearance in Game and Order in LevelSelectScreen
    public static final LevelData[] LEVEL_DATA = {
            new TutorialLevel1Data(),
            new TutorialLevel2Data(),
            new TutorialLevel3Data(),
            new TutorialLevel4Data(),
            new TutorialLevel5Data(),
            new TutorialLevel6Data(),
            new BomberfieldLevelData(),
            new SecretChamberLevelData(),
            new TangleOfPathsLevelData(),
            new IllusionLevelData()
    };

    public static final String LEVELS_BACKGRUND =           "menu/levelbg.png";
    public static final String LEVELS_BACKWARD =            "menu/back_aus.png";
    public static final String LEVELS_FORWARD =             "menu/forward_aus.png";
    public static final String LEVELS_ESCAPE =              "menu/escape_off.png";
    public static final String LEVELS_BACKWARD_PRESSED =    "menu/back_an.png";
    public static final String LEVELS_FORWARD_PRESSED =     "menu/forward_an.png";
    public static final String LEVELS_ESCAPE_PRESSED =      "menu/escape_on.png";
    public static final String LEVELS_FORWARD_DISABLED =    "menu/forward_grau.png";
    public static final String LEVELS_BACKWARD_DISABLED =   "menu/back_grau.png";

    public static void loadLevelSelectMenuTextures(){
        for (LevelData levelDatum : LEVEL_DATA) {
            manager.load(levelDatum.getButtonName(), Texture.class);
            manager.load(levelDatum.getButtonPressedName(), Texture.class);
        }
        manager.load(LEVELS_BACKGRUND, Texture.class);
        manager.load(LEVELS_BACKWARD, Texture.class);
        manager.load(LEVELS_FORWARD, Texture.class);
        manager.load(LEVELS_ESCAPE, Texture.class);
        manager.load(LEVELS_BACKWARD_PRESSED, Texture.class);
        manager.load(LEVELS_FORWARD_PRESSED, Texture.class);
        manager.load(LEVELS_ESCAPE_PRESSED, Texture.class);
        manager.load(LEVELS_FORWARD_DISABLED, Texture.class);
        manager.load(LEVELS_BACKWARD_DISABLED, Texture.class);
        manager.finishLoading();
    }

    public static final String GAME_BUTTON =            "menu/anknopf.png";
    public static final String GAME_BUTTON_PRESSED =    "menu/anknopf_an.png";
    public static final String END_BUTTON =             "menu/ende_aus.png";
    public static final String END_BUTTON_PRESSED =     "menu/ende_an.png";
    public static final String LEVEL_BUTTON =           "menu/level_aus.png";
    public static final String LEVEL_BUTTON_PRESSED =   "menu/level_an.png";
    public static final String BACKGROUND =             "menu/test.png";

    public static void loadMenuTextures(){
        manager.load(GAME_BUTTON, Texture.class);
        manager.load(GAME_BUTTON_PRESSED, Texture.class);
        manager.load(END_BUTTON, Texture.class);
        manager.load(END_BUTTON_PRESSED, Texture.class);
        manager.load(LEVEL_BUTTON, Texture.class);
        manager.load(LEVEL_BUTTON_PRESSED, Texture.class);
        manager.load(BACKGROUND, Texture.class);
        manager.finishLoading();

    }

    public static final String[] KEYS = {
            "keys/key_blue.png",
            "keys/key_green.png",
            "keys/key_red.png"
    };

    // Standard Direction: Top Left
    public static final String[] CAT_DIAGONAL =
            {"character/Cat_diagonal_leftFoot.png",
            "character/Cat_diagonal_middle.png",
            "character/Cat_diagonal_rightFoot.png"};
    // Standard Direction: Top
    public static final String[] CAT_VERTICAL =
            {"character/Cat_leftFoot.png",
            "character/Cat_middle.png",
            "character/Cat_rightFoot.png"};

    public static final String CAT_SITTING_LEFT =        "character/Cat_sitting_left.png";
    public static final String CAT_SITTING_RIGHT =       "character/Cat_sitting_right.png";

    public static void loadTextures() {
        manager.load(CAT_DIAGONAL[0], Texture.class);
        manager.load(CAT_DIAGONAL[1], Texture.class);
        manager.load(CAT_DIAGONAL[2], Texture.class);
        manager.load(CAT_VERTICAL[0], Texture.class);
        manager.load(CAT_VERTICAL[1], Texture.class);
        manager.load(CAT_VERTICAL[2], Texture.class);
        manager.load(CAT_SITTING_LEFT, Texture.class);
        manager.load(CAT_SITTING_RIGHT, Texture.class);

        for (String key: KEYS) {
            manager.load(key, Texture.class);
        }

        manager.finishLoading();
    }

    public static final String COLLECT_KEY_SOUND =     "sound/collect_key.mp3";
    public static final String STEP_ON_TRAP_SOUND =    "sound/cat_steps_on_trap.mp3";
    public static final String OPEN_DOOR_SOUND =       "sound/open_door.mp3";
    public static final String LEVEL_START_SOUND =     "sound/level_start_meow.mp3";
    public static final String LEVEL_FINISHED_SOUND =  "sound/level_finished.mp3";

    public static void loadAudio() {

        manager.load(COLLECT_KEY_SOUND, Sound.class);
        manager.load(STEP_ON_TRAP_SOUND, Sound.class);
        manager.load(OPEN_DOOR_SOUND, Sound.class);
        manager.load(LEVEL_FINISHED_SOUND, Sound.class);
        manager.load(LEVEL_START_SOUND, Sound.class);
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