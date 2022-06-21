package com.maze.game.maps;

public class PrototypeMap extends Map{

    private static final String TILEMAP_FILENAME = "prototyp_tilemap_64.tmx";
    private static final Float UNIT_SCALE = 1F;
    public PrototypeMap() {
        super(TILEMAP_FILENAME, UNIT_SCALE);
    }
}
