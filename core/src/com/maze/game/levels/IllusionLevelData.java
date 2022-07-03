package com.maze.game.levels;

/**
 * <h1>Illusion Level</h1>
 * Das Level zielt auf die Verwirrung des Spielers ab:
 * sinnlose Schlüssel, verschlossen bleibende Türen, mehrere potenzielle Zielpunkte, an deren Erreichbarkeit zu zweifeln ist.
 * „Es ist nicht alles belohnende Milch, was milchig glänzt.“
 *
 * @author   Jörn Drechsler
 */
public class IllusionLevelData extends LevelData{
    private final String name = "Illusion";
    private final String fileName = "illusion.tmx";
    private final String buttonName = "";
    private final String buttonPressedName = "";
    // TODO

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
