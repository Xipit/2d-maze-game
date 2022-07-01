package com.maze.game.levels;

/**
 * <h1>Erstes Tutorial Level</h1>
 * Bringt grundlegende "Victory" Bedingung bei
 *
 * @author  Hanno Witzleb
 */
public class Tutorial2Level extends Level{

    public static final String TILEMAP_FILENAME = "tutorial_2.tmx";

    public Tutorial2Level() {
        super(TILEMAP_FILENAME);
    }
}