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

    public static final String VICTORY_IMAGE = "assets/menu/victoryScreen.png";
    public static final String NEXT_LEVEL = "assets/menu/nextlvl_aus.png"; //TODO add custom next level texture
    public static final String NEXT_LEVEL_PRESSED = "assets/menu/nextlvl_an.png";

    public static void loadVictoryMenuTextures(){
        manager.load(VICTORY_IMAGE, Texture.class);
        manager.load(NEXT_LEVEL, Texture.class);
        manager.load(NEXT_LEVEL_PRESSED, Texture.class);
        manager.load(LEVELS_BACKWARD, Texture.class);
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
            new TangleOfPathsLevelData()
    };

    public static final String LEVELS_BACKGRUND = "assets/menu/levelbg.png";

    // TODO add custom Forward & Backward textures
    public static final String LEVELS_BACKWARD = "assets/menu/back_aus.png";
    public static final String LEVELS_FORWARD = "assets/menu/forward_aus.png";
    public static final String LEVELS_ESCAPE = "assets/menu/escape_off.png";
    public static final String LEVELS_BACKWARD_PRESSED = "assets/menu/back_an.png";
    public static final String LEVELS_FORWARD_PRESSED = "assets/menu/forward_an.png";
    public static final String LEVELS_ESCAPE_PRESSED = "assets/menu/escape_on.png";
    public static final String LEVELS_FORWARD_DISABLED = "assets/menu/forward_grau.png";
    public static final String LEVELS_BACKWARD_DISABLED = "assets/menu/back_grau.png";

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

    public static final String GAME_BUTTON =            "assets/menu/anknopf.png";
    public static final String GAME_BUTTON_PRESSED =    "assets/menu/anknopf_an.png";
    public static final String END_BUTTON =             "assets/menu/ende_aus.png";
    public static final String END_BUTTON_PRESSED =     "assets/menu/ende_an.png";
    public static final String LEVEL_BUTTON =           "assets/menu/level_aus.png";
    public static final String LEVEL_BUTTON_PRESSED =   "assets/menu/level_an.png";
    public static final String BACKGROUND =             "assets/menu/test.png";

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
            "assets/keys/key_blue.png",
            "assets/keys/key_green.png",
            "assets/keys/key_red.png"
    };

    // Standard Direction: Top Left
    public static final String[] CAT_DIAGONAL =
            {"assets/character/Cat_diagonal_leftFoot.png",
            "assets/character/Cat_diagonal_middle.png",
            "assets/character/Cat_diagonal_rightFoot.png"};
    // Standard Direction: Top
    public static final String[] CAT_VERTICAL =
            {"assets/character/Cat_leftFoot.png",
            "assets/character/Cat_middle.png",
            "assets/character/Cat_rightFoot.png"};

    public static final String CAT_SITTING_LEFT =        "assets/character/Cat_sitting_left.png";
    public static final String CAT_SITTING_RIGHT =        "assets/character/Cat_sitting_right.png";

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

    public static final String COLLECT_KEY_SOUND =     "assets/sound/collect_key.mp3";
    public static final String STEP_ON_TRAP_SOUND =    "assets/sound/cat_steps_on_trap.mp3";
    public static final String OPEN_DOOR_SOUND =       "assets/sound/open_door.mp3";
    public static final String LEVEL_START_SOUND =     "assets/sound/level_start_meow.mp3";
    public static final String LEVEL_FINISHED_SOUND =  "assets/sound/level_finished.mp3";

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