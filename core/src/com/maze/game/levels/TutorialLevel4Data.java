package com.maze.game.levels;

public class TutorialLevel4Data extends LevelData{
    private final String name = "Verschlüsselt";
    private final String fileName = "tutorial_4.tmx";
    private final String buttonName = "level4_aus.png";
    private final String buttonPressedName = "level4_an.png";

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
