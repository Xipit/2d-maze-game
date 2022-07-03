package com.maze.game.levels;

/**
 * <h1>TheSecretChambers Level</h1>
 * Dieses Level beinhaltet viele Räume und Türen, für die mehrere verschiedene Schlüssel benötigt werden.
 *
 * @author  Laurenz Oppelt
 */

public class SecretChamberLevelData extends LevelData{
    private final String name = "Secret Chamber";
    private final String fileName = "Level_theSecretChambers.tmx";
    private final String buttonName = "secret_aus.png";
    private final String buttonPressedName = "secret_an.png";

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
