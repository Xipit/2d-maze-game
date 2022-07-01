package com.maze.game.levels;

/**
 * <h1>Fünftes Tutorial Level</h1>
 * Führt Fallen und Türen zusammen ein, + Hinweise auf mehrere verschiedene Schlüssel
 *
 * @author  Hanno Witzleb
 */
public class Tutorial5Level extends Level{

    public static final String TILEMAP_FILENAME = "tutorial_5.tmx";

    public Tutorial5Level() {
        super(TILEMAP_FILENAME);
    }
}