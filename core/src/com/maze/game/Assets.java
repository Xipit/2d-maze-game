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

    public static final LevelData[] LEVEL_DATA = {
            new TutorialLevel1Data(),
            new TutorialLevel2Data(),
            new TutorialLevel3Data(),
            new TutorialLevel4Data(),
            new TutorialLevel5Data(),
            new BomberfieldLevelData(),
            new SecretChamberLevelData(),
            new TangleOfPathsLevelData()
    };

    public static final String LEVELS_BACKGRUND = "assets/menu/background_levelScreen.png";

    public static final String LEVELS_BACK = "assets/menu/tutorial1_button.png";
    public static final String LEVELS_FORWARD = "assets/menu/tutorial1_button.png";


    public static void loadLevelSelectMenuTextures(){
        for (LevelData levelDatum : LEVEL_DATA) {
            manager.load(levelDatum.getButtonName(), Texture.class);
            manager.load(levelDatum.getButtonPressedName(), Texture.class);
        }
        manager.load(LEVELS_BACKGRUND, Texture.class);
        manager.load(LEVELS_BACK, Texture.class);
        manager.load(LEVELS_FORWARD, Texture.class);
        manager.finishLoading();
    }

    public static final String GAME_BUTTON =            "assets/menu/anknopf.png";
    public static final String GAME_BUTTON_PRESSED =    "assets/menu/anknopf_an.png";
    public static final String END_BUTTON =             "assets/menu/endeknopf.png";
    public static final String END_BUTTON_PRESSED =     "assets/menu/endeknopf_an.png";
    public static final String LEVEL_BUTTON =           "assets/menu/Level_an.png";
    public static final String LEVEL_BUTTON_PRESSED =   "assets/menu/Level_aus.png";
    public static final String BACKGROUND =             "assets/menu/background_real.png";

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