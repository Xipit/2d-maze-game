package com.maze.game.levels;

public class TutorialLevel1Data extends LevelData{
    private final String name = "Das Verlangen nach Milch";
    private final String fileName = "tutorial_1.tmx";
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
