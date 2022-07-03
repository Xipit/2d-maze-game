package com.maze.game.levels;

import com.maze.game.Assets;

/**
 * <h1>LevelData</h1>
 * Datenklasse für alle relevanten Daten für ein Level.
 *
 * @author   Hanno Witzleb
 */
public class LevelData {

    protected final String textureAssetDirectory = "assets/menu/";
    protected final String tileMapAssetDirectory = "assets/tilemap/";

    public LevelData(){
    }

    public String getName(){
        return "";
    }
    public String getFileName(){
        return "";
    }
    public String getButtonName(){
        return "";
    }
    public String getButtonPressedName(){
        return "";
    }

    public int findIndex(){
        for(int i = 0; i < Assets.LEVEL_DATA.length; i++) {
            if(Assets.LEVEL_DATA[i] == this) {
                return i;
            }
        }
        return 0;
    }
}
