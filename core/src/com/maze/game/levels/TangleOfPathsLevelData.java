package com.maze.game.levels;

/**
 * <h1>TangleOfPaths Level</h1>
 * Dieses Level ist ein Wirrwarr aus Gängen.
 *
 * @author  Lilia Schneider
 */

public class TangleOfPathsLevelData extends LevelData{
    private final String name = "Tangle of Paths";
    private final String fileName = "tangleOfPaths.tmx";
    private final String buttonName = "tangle_aus.png";
    private final String buttonPressedName = "tangle_an.png";

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
