package com.maze.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    static int WIDTH, HEIGHT;
    static int WIDTH_PIXEL, HEIGHT_PIXEL;
    static int TILE_WIDTH, TILE_HEIGHT;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final TiledMapTileLayer tileLayer;

    public Map(String fileName, Float unitScale) {
        this.map = MazeGame.instance.assetManager.get(fileName, TiledMap.class);
        this.renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);
        this.tileLayer = (TiledMapTileLayer) this.map.getLayers().get("tiles");

        WIDTH = this.map.getProperties().get("width", Integer.class);
        HEIGHT = this.map.getProperties().get("height", Integer.class);
        TILE_WIDTH = this.map.getProperties().get("tilewidth", Integer.class);
        TILE_HEIGHT = this.map.getProperties().get("tileheight", Integer.class);
        WIDTH_PIXEL = WIDTH * TILE_WIDTH;
        HEIGHT_PIXEL = HEIGHT * TILE_HEIGHT;
    }

    public void render(OrthographicCamera camera) {
        this.renderer.setMap(this.map);
        this.renderer.setView(camera);
        this.renderer.render();
    }

    public void dispose() {
        this.map.dispose();
        this.renderer.dispose();
    }

    public Vector2 getWallCollision(Vector2 moveVector, Position playerPosition) {
        ArrayList<Point> cornerPoints = getCornerPoints(moveVector, playerPosition);


        Point tileIndex = getTileIndex(pixelCoordinates);

        // Pixel genaue Prüfung der Eckpunkte des rechteckigen Spielcharakters
        TiledMapTileLayer.Cell[] cells = {
                this.tileLayer.getCell(tileIndex. / TILE_WIDTH, y / TILE_HEIGHT),
                this.tileLayer.getCell(x / TILE_WIDTH, (y + player.shape.height) / TILE_HEIGHT),
                this.tileLayer.getCell((x + player.shape.width) / TILE_WIDTH, y / TILE_HEIGHT),
                this.tileLayer.getCell((x + player.shape.width) / TILE_WIDTH, (y + player.shape.height) / TILE_HEIGHT)
        };

        for (TiledMapTileLayer.Cell cell : cells) {
            if (cell != null && cell.getTile() != null) {
                if (cell.getTile().getProperties().containsKey("wall_collision"))
                    return true;
            }
        }

    }

    private Point getTileIndex(Point pixelCoordinates){
        int xIndex = (pixelCoordinates.x - pixelCoordinates.x % TILE_WIDTH) / TILE_WIDTH;
        int yIndex = (pixelCoordinates.y - pixelCoordinates.y % TILE_HEIGHT) / TILE_HEIGHT;
        return new Point(xIndex, yIndex);
    }

    private ArrayList<Point> getCornerPoints(Vector2 moveVector, Position playerPosition) {
        // Returns the position of the corners of the position object (player) that are at the front of the movement direction (and therefore are to be checked for collisions).
        ArrayList<Point> corners = new ArrayList<> ();

        if (moveVector.x < 0 || moveVector.y > 0){
            corners.add(new Point(playerPosition.xMin, playerPosition.yMax)); // topLeft
        }
        if (moveVector.x > 0 || moveVector.y > 0){
            corners.add(new Point(playerPosition.xMax, playerPosition.yMax)); // topRight
        }
        if (moveVector.x < 0 || moveVector.y < 0) {
            corners.add(new Point(playerPosition.xMin, playerPosition.yMin)); // bottomLeft
        }
        if (moveVector.x > 0 || moveVector.y < 0) {
            corners.add(new Point(playerPosition.xMax, playerPosition.yMin)); // bottomRight
        }

        return corners;
    }
}