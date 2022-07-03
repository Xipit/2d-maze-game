package com.maze.game.levels;

/**
 * <h1>Bomberfield Level</h1>
 *
 *
 * @author   Simeon Baumann
 */
public class BomberfieldLevelData extends LevelData{
    private final String name = "Bomberfield";
    private final String fileName = "bomberfield.tmx";
    private final String buttonName = "Bomber_aus.png";
    private final String buttonPressedName = "bomber_an.png";

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
