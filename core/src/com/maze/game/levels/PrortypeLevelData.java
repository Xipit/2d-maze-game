package com.maze.game.levels;

/**
 * <h1>Prototyp LevelData</h1>
 * Prototyp Level um Spielmechaniken schnell und einfach zu testen
 *
 * @author   Hanno Witzleb, Jörn Drechsler
 */
public class PrortypeLevelData extends LevelData{
    private final String name = "Prototyp Level";
    private final String fileName = "prototyp.tmx";
    private final String buttonName = "tutorial1_button.png";
    private final String buttonPressedName = "tutorial1_buttonPressed.png";

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
