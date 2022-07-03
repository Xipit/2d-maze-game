package com.maze.game.levels;

/**
 * <h1>Sechstes Tutorial Level</h1>
 * Führt alle Spielmechaniken zusammen.
 *
 * @author   Hanno Witzleb
 */
public class TutorialLevel6Data extends LevelData{
    private final String name = "Door Decisions";
    private final String fileName = "tutorial_6.tmx";
    private final String buttonName = "level6_aus.png";
    private final String buttonPressedName = "level6_an.png";

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getFileName() {
        return tileMapAssetDirectory + fileName;
    }

    @Override
    public String getButtonName() {
        return textureAssetDirectory + buttonName;
    }

    @Override
    public String getButtonPressedName() {
        return textureAssetDirectory + buttonPressedName;
    }
}