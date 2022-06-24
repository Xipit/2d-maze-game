package com.maze.game.types;

import java.awt.*;

public class Tile {
    public LayerTile base;
    public LayerTile interaction;

    public Tile(LayerTile base, LayerTile interaction){
        this.base = base;
        this.interaction = interaction;
    }

    public static  Point getIndex(Point pixelCoordinates, int tileWidth, int tileHeight){
        int xIndex = (pixelCoordinates.x - pixelCoordinates.x % tileHeight) / tileHeight;
        int yIndex = (pixelCoordinates.y - pixelCoordinates.y % tileWidth) / tileWidth;
        return new Point(xIndex, yIndex);
    }

    public static boolean containsKey(LayerTile tile, String key){
        return tile != null && tile.properties.containsKey(key);
    }
}

