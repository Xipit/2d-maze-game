package com.maze.game;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.Iterator;

public class Tile {
    public Point index;
    public MapProperties properties;
    public TiledMapTile tile;

    public Tile(Point index, MapProperties properties, TiledMapTile tile){
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

    public static boolean containsKey(Tile tile, String key){
        return tile != null && tile.properties.containsKey(key);
    }
}
