package com.maze.game.types;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.maze.game.maps.Map;

import java.awt.*;

public class Tile {
    public Point index;
    public MapProperties properties;
    public TiledMapTile tile;
    public int cornerIndex;

    public Tile(Point index, MapProperties properties, TiledMapTile tile, int cornerIndex){
        this.index = index;
        this.properties = properties;
        this.tile = tile;
        this.cornerIndex = cornerIndex;
    }

    public Point getPosition(int tileWidth, int tileHeight){
        return new Point(index.x * tileWidth, index.y * tileHeight);
    }

    public Point getCollisionEdge(int tileWidth, int tileHeight){
        Point collisionEdge = getPosition(tileWidth, tileHeight); // bottomLeft of Tile

        // PREPARATION for future collision (smaller than 1 tile collision)
        // read from properties to determine following variables
        int collisionWidth = tileWidth;
        int collisionHeight = tileHeight;

        int collisionOffsetX = 0;
        int collisionOffsetY = 0;

        if(cornerIndex == Map.Corner.topLeft.ordinal()){
            collisionEdge.x += collisionOffsetX + collisionWidth;
            collisionEdge.y += collisionOffsetY -1;
        }
        else if(cornerIndex == Map.Corner.topRight.ordinal()){
            collisionEdge.x += collisionOffsetX -1;
            collisionEdge.y += collisionOffsetY -1;
        }
        else if(cornerIndex == Map.Corner.bottomLeft.ordinal()){
            collisionEdge.x += collisionOffsetX + collisionWidth;
            collisionEdge.y += collisionOffsetY + collisionHeight;
        }
        else if(cornerIndex == Map.Corner.bottomRight.ordinal()){
            collisionEdge.x += collisionOffsetX -1;
            collisionEdge.y += collisionOffsetY + collisionHeight;
        }

        return collisionEdge;
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
