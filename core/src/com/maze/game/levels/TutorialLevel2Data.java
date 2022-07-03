package com.maze.game.levels;

public class TutorialLevel2Data extends LevelData{
    private final String name = "TutorialLevel2";
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
