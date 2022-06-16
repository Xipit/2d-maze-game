package com.maze.game;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.Iterator;

public class Tile {
    public Point index;
    public Iterator properties;
    public TiledMapTile tile;

    public Tile(Point index, Iterator properties, TiledMapTile tile){
        this.index = index;
        this.properties = properties;
        this.tile = tile;
    }

    public Point getPosition(int tileWidth, int tileHeight){
        return new Point(index.x * tileWidth, index.y * tileHeight);
    }

    public static  Point getIndex(Point pixelCoordinates, int tileWidth, int tileHeight){
        int xIndex = (pixelCoordinates.x - pixelCoordinates.x % tileHeight) / tileHeight;
        int yIndex = (pixelCoordinates.y - pixelCoordinates.y % tileWidth) / tileWidth;
        return new Point(xIndex, yIndex);
    }
}
