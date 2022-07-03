package com.maze.game.levels;

/**
 * <h1>Zweites Tutorial Level</h1>
 * Bringt die generelle Atmosphäre und den Levelaufbau näher.
 *
 * @author   Hanno Witzleb
 */
public class TutorialLevel2Data extends LevelData{
    private final String name = "Maze-merising";
    private final String fileName = "tutorial_2.tmx";
    private final String buttonName = "level2_aus.png";
    private final String buttonPressedName = "level2_an.png";

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
