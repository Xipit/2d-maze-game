package com.maze.game.levels;

public class TutorialLevel5Data extends LevelData{
    private final String name = "Unerreichbar?";
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
