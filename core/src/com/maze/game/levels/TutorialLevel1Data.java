package com.maze.game.levels;

public class TutorialLevel1Data extends LevelData{
    private final String name = "Das Verlangen nach Milch";
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
