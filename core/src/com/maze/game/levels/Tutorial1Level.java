package com.maze.game.levels;

/**
 * <h1>Erstes Tutorial Level</h1>
 * Bringt grundlegende "Victory" Bedingung bei
 *
 * @author  Hanno Witzleb
 */
public class Tutorial1Level extends Level{

    public static final String TILEMAP_FILENAME = "tutorial_1.tmx";

    public Tutorial1Level() {
        super(TILEMAP_FILENAME);
    }
}
