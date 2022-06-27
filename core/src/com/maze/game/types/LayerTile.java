package com.maze.game.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.maze.game.Properties;
import com.maze.game.maps.Map;

import java.awt.*;

public class LayerTile {
    public Point index;
    public MapProperties properties;
    public TiledMapTile tile;
    private final TiledMapTileLayer.Cell cell;
    private final int playerCornerIndex;

    public LayerTile(Point index, MapProperties properties, TiledMapTile tile, TiledMapTileLayer.Cell cell, int playerCornerIndex) {
        this.index = index;
        this.properties = properties;
        this.tile = tile;
        this.cell = cell;
        this.playerCornerIndex = playerCornerIndex;
    }

    public Point getPosition(int tileWidth, int tileHeight) {
        return new Point(index.x * tileWidth, index.y * tileHeight);
    }

    public void updateTile(TiledMapTile tile){
        this.cell.setTile(tile);
        this.tile = tile;
        this.properties = tile.getProperties();
    }

    public boolean isInCollisionBox(Point cornerPosition, int tileWidth, int tileHeight){
        Point collisionEdge = getCollisionEdge(tileWidth, tileHeight);

        if (playerCornerIndex == Map.Corner.topLeft.ordinal()) {
            return (cornerPosition.x <= collisionEdge.x && cornerPosition.y >= collisionEdge.y);
        } else if (playerCornerIndex == Map.Corner.topRight.ordinal()) {
            return (cornerPosition.x >= collisionEdge.x && cornerPosition.y >= collisionEdge.y);
        } else if (playerCornerIndex == Map.Corner.bottomLeft.ordinal()) {
            return (cornerPosition.x <= collisionEdge.x && cornerPosition.y <= collisionEdge.y);
        } else if (playerCornerIndex == Map.Corner.bottomRight.ordinal()) {
            return (cornerPosition.x >= collisionEdge.x && cornerPosition.y <= collisionEdge.y);
        }
        return false;
    }

    public Point getCollisionEdge(int tileWidth, int tileHeight) {
        Point collisionEdge = getPosition(tileWidth, tileHeight); // bottomLeft of Tile

        // PREPARATION for future collision (smaller than 1 tile collision)
        // read from properties to determine following variables

        int collisionWidth = tileWidth;
        int collisionHeight = tileHeight;

        int collisionOffsetX = 0;
        int collisionOffsetY = 0;

        // adjust collision for doors to be able to touch the tile to unlock it
        if(this.properties.containsKey(Properties.DOOR_DIRECTION_KEY)){
            Properties.DoorDirection doorDirection = Properties.DoorDirection.valueOf(this.properties.get(Properties.DOOR_DIRECTION_KEY).toString());

            final int collisionInset = 7;
            switch (doorDirection){
                case N:
                    collisionOffsetY = 0;
                    collisionHeight -= collisionInset;
                    break;

                case S:
                    collisionOffsetY = collisionInset;
                    collisionHeight -= collisionInset;
                    break;

                case E:
                    collisionOffsetX = 0;
                    collisionWidth -= collisionInset;
                    break;

                case W:
                    collisionOffsetX = collisionInset;
                    collisionWidth -= collisionInset;
                    break;

                default:
                    break;
            }
        }


        if (playerCornerIndex == Map.Corner.topLeft.ordinal()) {
            collisionEdge.x += collisionOffsetX + collisionWidth;
            collisionEdge.y += collisionOffsetY - 1;
        } else if (playerCornerIndex == Map.Corner.topRight.ordinal()) {
            collisionEdge.x += collisionOffsetX - 1;
            collisionEdge.y += collisionOffsetY - 1;
        } else if (playerCornerIndex == Map.Corner.bottomLeft.ordinal()) {
            collisionEdge.x += collisionOffsetX + collisionWidth;
            collisionEdge.y += collisionOffsetY + collisionHeight;
        } else if (playerCornerIndex == Map.Corner.bottomRight.ordinal()) {
            collisionEdge.x += collisionOffsetX - 1;
            collisionEdge.y += collisionOffsetY + collisionHeight;
        }

        return collisionEdge;
    }

}
