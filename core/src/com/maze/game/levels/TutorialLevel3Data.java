package com.maze.game.levels;

public class TutorialLevel3Data extends LevelData{
    private final String name = "TutorialLevel3";
    private final String fileName = "tutorial_3.tmx";
    private final String buttonName = "level3_aus.png";
    private final String buttonPressedName = "level3_an.png";

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
