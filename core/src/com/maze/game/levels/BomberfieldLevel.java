package com.maze.game.levels;

/**
 * <h1>Bomberfield Level</h1>
 * Dieses Level besitzt ein Feld mit vielen Fallen --> Bomberfield
 *
 * @author  Simeon Baumann
 */

public class BomberfieldLevel extends Level {
    public static final String TILEMAP_FILENAME = "bomberfield.tmx";

    public BomberfieldLevel() { super(TILEMAP_FILENAME); }
}