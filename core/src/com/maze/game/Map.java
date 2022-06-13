package com.maze.game;

import com.badlogic.gdx.Gdx;
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

    /*
        Corner Indices:
        0 - 1
        |   |
        2 - 3
    */
    public Vector2 accountForCollision(Vector2 moveVector, Position previousPosition) {
        Gdx.app.log("MazeGame", "xMin  " + previousPosition.xMin);
        Gdx.app.log("MazeGame", "xMax  " + previousPosition.xMax);
        Gdx.app.log("MazeGame", "yMin  " + previousPosition.yMin);
        Gdx.app.log("MazeGame", "yMax  " + previousPosition.yMax);
        Position position = previousPosition.update(moveVector);

        Point[] cornerPoints = getCornerPoints(moveVector, position);

        TiledMapTileLayer.Cell[] potentialCollisionCells = {null, null, null, null};
        Point[] cornerPointTileIndices = {null, null, null, null};

        for (int i = 0; i < cornerPoints.length; i ++) {
            if(cornerPoints[i] == null) continue;
            
            Point cornerPointTileIndex = getTileIndex(cornerPoints[i]);
            cornerPointTileIndices[i] = cornerPointTileIndex;
            potentialCollisionCells[i] = (this.tileLayer.getCell(cornerPointTileIndex.x, cornerPointTileIndex.y));
        }

        ArrayList<Vector2> correctionVectors = new ArrayList<>();



        for(int i = 0; i < potentialCollisionCells.length; i ++){
            if(potentialCollisionCells[i] == null || potentialCollisionCells[i].getTile() == null){
                continue;
            }
            if (potentialCollisionCells[i].getTile().getProperties().containsKey("wall_collision")){
                // info
                // - corner Position in pixels
                // - which cornerPosition it is -> index
                // - tile Position as index & in pixels
                // - moveVector
                // - previous and new Position

                // need to know per tile from which direction we are entering
                // playerPosition center maybe?

                Point tilePosition = new Point(cornerPointTileIndices[i].x * TILE_WIDTH, cornerPointTileIndices[i].y * TILE_HEIGHT); // bottomLeft of Tile
                //adjust tilePosition to match the relevant corner
                tilePosition.x += (i == 0 || i == 2) ? TILE_WIDTH : 0;
                tilePosition.y += (i == 2 || i == 3) ? TILE_HEIGHT : 0;

                Gdx.app.log("MazeGame", "tileIndex: " + i + " -> " + cornerPointTileIndices[i].toString() + ", " + tilePosition.toString());


                Vector2 correctionVector = new Vector2(tilePosition.x - cornerPoints[i].x, tilePosition.y - cornerPoints[i].y);

                correctionVectors.add(correctionVector); // pushes player into available space
            }
        }


        /*
            0 - 1
            |   |
            2 - 3
        */
        // check if 2 X or Y values are the same
        Boolean considerX = true, considerY = true;


        if(cornerPointRelevantAndCollides(0, cornerPoints, potentialCollisionCells) && cornerPointRelevantAndCollides(1, cornerPoints, potentialCollisionCells)
                && cornerPoints[0].y == cornerPoints[1].y
                || cornerPointRelevantAndCollides(2, cornerPoints, potentialCollisionCells) && cornerPointRelevantAndCollides(3, cornerPoints, potentialCollisionCells)
                && cornerPoints[2].y == cornerPoints[3].y){
            considerX = false;
        }
        if(cornerPointRelevantAndCollides(0, cornerPoints, potentialCollisionCells) && cornerPointRelevantAndCollides(2, cornerPoints, potentialCollisionCells)
                && cornerPoints[0].x == cornerPoints[2].x
                || cornerPointRelevantAndCollides(1, cornerPoints, potentialCollisionCells) && cornerPointRelevantAndCollides(3, cornerPoints, potentialCollisionCells)
                && cornerPoints[1].x == cornerPoints[3].x){
            considerY = false;
        }
        Gdx.app.log("MazeGame", "considerX " + considerX.toString());
        Gdx.app.log("MazeGame", "considerY " + considerY.toString());



        Vector2 accumulatedCorrectionVector = new Vector2(0, 0);
        for (Vector2 correctionVector:
             correctionVectors) {
            Vector2 correction = new Vector2(0,0);
            correction.x = considerX ? (Math.abs(accumulatedCorrectionVector.x) > Math.abs(correctionVector.x) ? accumulatedCorrectionVector.x : correctionVector.x) : 0;
            correction.y = considerY ? (Math.abs(accumulatedCorrectionVector.y) > Math.abs(correctionVector.y) ? accumulatedCorrectionVector.y : correctionVector.y) : 0;

            accumulatedCorrectionVector = correction;
            Gdx.app.log("MazeGame", "correctionVector: " + correctionVector.toString());
        }



        Gdx.app.log("MazeGame", "correction: " + accumulatedCorrectionVector.toString());
        return accumulatedCorrectionVector;

    }

    private Point getTileIndex(Point pixelCoordinates){
        int xIndex = (pixelCoordinates.x - pixelCoordinates.x % TILE_WIDTH) / TILE_WIDTH;
        int yIndex = (pixelCoordinates.y - pixelCoordinates.y % TILE_WIDTH) / TILE_WIDTH;
        return new Point(xIndex, yIndex);
    }

    private Point[] getCornerPoints(Vector2 moveVector, Position playerPosition) {
        Point[] corners = {null, null, null, null};

        ArrayList<Integer> cornerIndices = new ArrayList<>();

        /*
            0 - 1
            |   |
            2 - 3
        */

/*
        corners[0] = new Point(playerPosition.xMin, playerPosition.yMax); // topLeft
        corners[1] = new Point(playerPosition.xMax, playerPosition.yMax); // topRight
        corners[2] = new Point(playerPosition.xMin, playerPosition.yMin); // bottomLeft
        corners[3] = new Point(playerPosition.xMax, playerPosition.yMin); // bottomRight
*/

        if (moveVector.x < 0 || moveVector.y > 0){
            corners[0] = new Point(playerPosition.xMin, playerPosition.yMax); // topLeft
        }
        if (moveVector.x > 0 || moveVector.y > 0){
            corners[1] = new Point(playerPosition.xMax, playerPosition.yMax); // topRight
        }
        if (moveVector.x < 0 || moveVector.y < 0) {
            corners[2] = new Point(playerPosition.xMin, playerPosition.yMin); // bottomLeft
        }
        if (moveVector.x > 0 || moveVector.y < 0) {
            corners[3] = new Point(playerPosition.xMax, playerPosition.yMin); // bottomRight
        }

        Gdx.app.log("MazeGame", "xMin  " + playerPosition.xMin);
        Gdx.app.log("MazeGame", "xMax  " + playerPosition.xMax);
        Gdx.app.log("MazeGame", "yMin  " + playerPosition.yMin);
        Gdx.app.log("MazeGame", "yMax  " + playerPosition.yMax);



        Gdx.app.log("MazeGame", "topLeft" + ((corners[0] != null) ? corners[0].toString() : ""));
        Gdx.app.log("MazeGame", "topRight" + ((corners[1] != null) ? corners[1].toString() : ""));
        Gdx.app.log("MazeGame", "bottomLeft" + ((corners[2] != null) ? corners[2].toString() : ""));
        Gdx.app.log("MazeGame", "bottomRight" + ((corners[3] != null) ? corners[3].toString() : ""));

        return corners;
    }

    /*
       0 - 1
       |   |
       2 - 3
   */
    Boolean cornerPointRelevantAndCollides(int i, Point[] cornerPoints, TiledMapTileLayer.Cell[] potentialCollisionCells){
        if(cornerPoints[i] != null && potentialCollisionCells[i].getTile().getProperties().containsKey("wall_collision")){
            return true;
        }
        return false;
    }
}