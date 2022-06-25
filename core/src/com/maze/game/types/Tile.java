package com.maze.game.types;

import com.maze.game.Properties;

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

    public boolean collidesWithWall(){
        return base.properties.containsKey(Properties.COLLISION_KEY);
    }
    public boolean collidesWithDoor(Point cornerPosition, int tileWidth, int tileHeight){
        boolean tileIsDoor = base.properties.containsKey(Properties.DOOR_DIRECTION_KEY)
                && base.properties.containsKey(Properties.DOOR_TYPE_KEY);

        if(tileIsDoor){
            return this.base.isInCollisionBox(cornerPosition, tileWidth, tileHeight);
        }
        return false;
    }
}

