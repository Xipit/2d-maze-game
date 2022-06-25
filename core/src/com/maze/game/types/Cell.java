package com.maze.game.types;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Cell {
    public TiledMapTileLayer.Cell base;
    public TiledMapTileLayer.Cell interaction;

    public Cell(TiledMapTileLayer.Cell base, TiledMapTileLayer.Cell interaction){
        this.base = base;
        this.interaction = interaction;
    }
}