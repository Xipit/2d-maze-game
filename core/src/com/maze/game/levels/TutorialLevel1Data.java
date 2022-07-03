package com.maze.game.levels;

/**
 * <h1>Erstes Tutorial Level</h1>
 * Bringt die "Win-Condition" bei.
 *
 * @author   Hanno Witzleb
 */
public class TutorialLevel1Data extends LevelData{
    private final String name = "The desire of milk";
    private final String fileName = "tutorial_1.tmx";
    private final String buttonName = "level1_aus.png";
    private final String buttonPressedName = "level1_an.png";

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
