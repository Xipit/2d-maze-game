package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.Arrays;

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

    public Vector2 accountForCollision(Vector2 moveVector, Position position) {
        // This implementation works only if the motion vector has a smaller length than the length of the tiles.
        // It is only checked for collision at the prospective position, without considering that several tiles might collide on that way.

        Position potentialPosition = position.calculateNewPosition(moveVector);

        Vector2 correctionVector = new Vector2(0, 0);

        // Prevent the map boundaries from being exceeded.
        if(potentialPosition.xMin  < 0) correctionVector.x = -potentialPosition.xMin;
        if(potentialPosition.xMax > WIDTH_PIXEL) correctionVector.x = -(potentialPosition.xMax - WIDTH_PIXEL);
        if(potentialPosition.yMin < 0) correctionVector.y = -potentialPosition.yMin;
        if(potentialPosition.yMax > HEIGHT_PIXEL) correctionVector.y = -(potentialPosition.yMax - HEIGHT_PIXEL);

        Point[] potentialCornerPoints = getCornerPoints(moveVector, potentialPosition);

        Point[] tileIndicesCollisionCells = {null, null, null, null};
        for (int i = 0; i < potentialCornerPoints.length; i ++) {
            if(potentialCornerPoints[i] == null) continue;

            Point potentialCornerPoint = getTileIndex(potentialCornerPoints[i]);
            TiledMapTileLayer.Cell cell = this.tileLayer.getCell(potentialCornerPoint.x, potentialCornerPoint.y);
            if(cell == null) continue;

            if(cell.getTile().getProperties().containsKey("wall_collision")) {
                tileIndicesCollisionCells[i] = potentialCornerPoint;
            }
        }
        Gdx.app.log("MazeGame", "potentialCornerPoints: " + Arrays.toString(potentialCornerPoints));
        Gdx.app.log("MazeGame", "tileIndicesCollisionCells: " + Arrays.toString(tileIndicesCollisionCells));

        // Reduce the number of colliding cells to be considered for calculation to one.
        boolean double_motion_ignore_x = false, double_motion_ignore_y = false;
        Point tileIndicesCollisionCellOfInterest = null;

        /*
            2 - 3
            |   |
            0 - 1
        */

        // triple collision (with "double" direction of movement):
        // The enclosed cell is considered.
        if(tileIndicesCollisionCells[2] != null && tileIndicesCollisionCells[0] != null && tileIndicesCollisionCells[1] != null)
            tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[0];
        else if(tileIndicesCollisionCells[0] != null && tileIndicesCollisionCells[1] != null && tileIndicesCollisionCells[3] != null)
            tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[1];
        else if(tileIndicesCollisionCells[1] != null && tileIndicesCollisionCells[3] != null && tileIndicesCollisionCells[2] != null)
            tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[3];
        else if(tileIndicesCollisionCells[3] != null && tileIndicesCollisionCells[2] != null && tileIndicesCollisionCells[0] != null)
            tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[2];
        else {
            if (moveVector.x != 0 ^ moveVector.y != 0) {
                // single or double collision with single direction of movement:
                // The choice of cell does not matter.
                for (Point tileIndicesCollisionCell : tileIndicesCollisionCells) {
                    if (tileIndicesCollisionCell != null) {
                        tileIndicesCollisionCellOfInterest = tileIndicesCollisionCell;
                        break;
                    }
                }
            }
            else {
                // single or double collision with "double" direction of movement:
                // Selection of the cell of the colliding corner that is further in the direction of movement.
                if (moveVector.x < 0 && moveVector.y < 0)
                    tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[0];
                else if (moveVector.x > 0 && moveVector.y < 0)
                    tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[1];
                else if (moveVector.x < 0 && moveVector.y > 0)
                    tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[2];
                else if (moveVector.x > 0 && moveVector.y > 0)
                    tileIndicesCollisionCellOfInterest = tileIndicesCollisionCells[3];

                Gdx.app.log("MazeGame", "double collision");
                // double collision
                if (tileIndicesCollisionCellOfInterest == null) {}
                else if ((tileIndicesCollisionCells[0] != null && tileIndicesCollisionCells[2] != null) || (tileIndicesCollisionCells[1] != null && tileIndicesCollisionCells[3] != null))
                    double_motion_ignore_y = true;
                else if ((tileIndicesCollisionCells[0] != null && tileIndicesCollisionCells[1] != null) || (tileIndicesCollisionCells[2] != null && tileIndicesCollisionCells[3] != null))
                    double_motion_ignore_x = true;
                // single collision
                else {
                    Gdx.app.log("MazeGame", "single collision");

                    int delta_x, delta_y;
                    if (moveVector.x > 0)
                        delta_x = potentialPosition.xMax - tileIndicesCollisionCellOfInterest.x * TILE_WIDTH + 1;
                    else delta_x = (tileIndicesCollisionCellOfInterest.x + 1) * TILE_WIDTH - potentialPosition.xMin + 1;
                    if (moveVector.y > 0)
                        delta_y = potentialPosition.yMax - tileIndicesCollisionCellOfInterest.y * TILE_HEIGHT +  1;
                    else delta_y = (tileIndicesCollisionCellOfInterest.y + 1) * TILE_HEIGHT - potentialPosition.yMin + 1;

                    // delta(x) > delta(y) -> ignore(x) | delta(y) >= delta(x) -> ignore(y)
                    if (delta_x > delta_y) {
                        Gdx.app.log("MazeGame", "double_motion_ignore_x");
                        double_motion_ignore_x = true;}
                    else {
                        Gdx.app.log("MazeGame", "double_motion_ignore_y");
                        double_motion_ignore_y = true;
                    }
                    // todo refine
                }
            }
        }
        Gdx.app.log("MazeGame", "tileIndicesCollisionCellOfInterest: " + tileIndicesCollisionCellOfInterest);

        // Return vector that corrects the movement vector so that there is no longer a collision and the intended movement is maximized.
        if(tileIndicesCollisionCellOfInterest == null) return correctionVector;

        if(moveVector.x > 0 && !double_motion_ignore_x)
            correctionVector.add(-(potentialPosition.xMax - tileIndicesCollisionCellOfInterest.x * TILE_WIDTH) - 1, 0);
        else if(moveVector.x < 0 && !double_motion_ignore_x)
            correctionVector.add((tileIndicesCollisionCellOfInterest.x + 1) * TILE_WIDTH - potentialPosition.xMin + 1, 0);

        if (moveVector.y > 0 && !double_motion_ignore_y)
            correctionVector.add(0, -(potentialPosition.yMax - tileIndicesCollisionCellOfInterest.y * TILE_HEIGHT) - 1);
        else if (moveVector.y < 0 && !double_motion_ignore_y)
            correctionVector.add(0, (tileIndicesCollisionCellOfInterest.y + 1) * TILE_HEIGHT - potentialPosition.yMin + 1);

        Gdx.app.log("MazeGame", "position: xMin " + position.xMin + " xMax " + position.xMax + " yMin " + position.yMin + " yMax " + position.yMax);
        Gdx.app.log("MazeGame", "potentialPosition: xMin " + potentialPosition.xMin + " xMax " + potentialPosition.xMax + " yMin " + potentialPosition.yMin + " yMax " + potentialPosition.yMax);
        Gdx.app.log("MazeGame", "correctionVector: " + correctionVector.toString());
        return  correctionVector;
    }

    private Point getTileIndex(Point pixelCoordinates){
        // x<=64 0 | 65<=x<=128 1 | 129<=x<=193 2 ...
        // examples:
        // 64 : (64 - mode(63, 64)) / 64 = (64 - 63) / 64 ≈ 0
        // 65 : (65 - mode(64, 64)) / 64 = (65 - 0) / 64 ≈ 1
        // 128 : (128 - mode(127, 64)) / 64 = (128 - 63) / 64 ≈ 1
        // 129 : (129 - mode(128, 64)) / 64 = (129 - 0) / 64 ≈ 2

        int xIndex = (pixelCoordinates.x - (pixelCoordinates.x - 1) % TILE_WIDTH) / TILE_WIDTH;
        int yIndex = (pixelCoordinates.y - (pixelCoordinates.y - 1) % TILE_WIDTH) / TILE_WIDTH;

        return new Point(xIndex, yIndex);
    }

    private Point[] getCornerPoints(Vector2 moveVector, Position playerPosition) {
        // Returns the position of the corners of the position object (player) that are at the front of the movement direction (and therefore are to be checked for collisions).
        // With single direction of movement 2, with "double" direction of movement 3 corners.

        /*
            2 - 3
            |   |
            0 - 1
        */

        Point[] corners = {null, null, null, null};

        if (moveVector.x < 0 || moveVector.y < 0) {
            corners[0] = new Point(playerPosition.xMin, playerPosition.yMin); // bottomLeft
        }
        if (moveVector.x > 0 || moveVector.y < 0) {
            corners[1] = new Point(playerPosition.xMax, playerPosition.yMin); // bottomRight
        }
        if (moveVector.x < 0 || moveVector.y > 0){
            corners[2] = new Point(playerPosition.xMin, playerPosition.yMax); // topLeft
        }
        if (moveVector.x > 0 || moveVector.y > 0){
            corners[3] = new Point(playerPosition.xMax, playerPosition.yMax); // topRight
        }

        return corners;
    }
}