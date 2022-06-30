package com.maze.game.levels;

/**
 * <h1>Prototyp Level</h1>
 * Benutzt zum Testen von Gameplay Mechanik & Tileset
 *
 * @author  Hanno Witzleb, Jörn Drechsler
 */
public class PrototypeLevel extends Level {

    public static final String TILEMAP_FILENAME = "prototyp.tmx";
    public PrototypeLevel() {
        super(TILEMAP_FILENAME);
    }
}
