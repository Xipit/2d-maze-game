package com.maze.game.levels;

/**
 * <h1>Fünftes Tutorial Level</h1>
 * Kombiniert Tür & Schlüssel mit Fallen, Weist auf mehrere Türen & Schlüssel hin.
 *
 * @author   Hanno Witzleb
 */
public class TutorialLevel5Data extends LevelData{
    private final String name = "Unreachable?";
    private final String fileName = "tutorial_5.tmx";
    private final String buttonName = "level5_aus.png";
    private final String buttonPressedName = "level5_an.png";

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
