package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

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

    public enum Corner{
        topLeft, topRight, bottomLeft, bottomRight
    }

    /*
        Corner Indices:
        0 - 1
        |   |
        2 - 3
    */
    Point[] cornerPositions = {null, null, null, null};
    Tile[] cornerTiles = {null, null, null, null};

    public void calculateCornerData(Vector2 directionVector, PlayerPosition playerPosition) {
        cornerPositions = calculateCornerPositions(directionVector, playerPosition);

        for (int i = 0; i < Corner.values().length; i ++) {
            if(cornerPositions[i] == null) continue;

            Point cornerTileIndex = Tile.getIndex(cornerPositions[i], TILE_WIDTH, TILE_HEIGHT);

            Tile cornerTile = new Tile(
                cornerTileIndex,
                this.tileLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile().getProperties(),
                this.tileLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile());

            cornerTiles[i] = cornerTile;
        }
    }

    private Vector2 calculateMoveCorrectionVector(PlayerPosition playPosition, int cornerIndex, boolean considerX, boolean considerY) {
        Point edgeOfTile = cornerTiles[cornerIndex].getPosition(TILE_WIDTH, TILE_HEIGHT); // bottomLeft of Tile

        if(cornerIndex == Corner.topLeft.ordinal()){
            edgeOfTile.x += TILE_WIDTH;
            edgeOfTile.y += -1;
        }
        else if(cornerIndex == Corner.topRight.ordinal()){
            edgeOfTile.x += -1;
            edgeOfTile.y += -1;
        }
        else if(cornerIndex == Corner.bottomLeft.ordinal()){
            edgeOfTile.x += TILE_WIDTH;
            edgeOfTile.y += TILE_HEIGHT;
        }
        else if(cornerIndex == Corner.bottomRight.ordinal()){
            edgeOfTile.x += -1;
            edgeOfTile.y += TILE_HEIGHT;
        }

        Gdx.app.log("MAZEGAME", "cornerPositions[" + cornerIndex + "] - " + cornerPositions[cornerIndex]);
        Gdx.app.log("MAZEGAME", "cornerTiles[" + cornerIndex + "] - " + cornerTiles[cornerIndex].index);


        return new Vector2(
                considerX ? (edgeOfTile.x - cornerPositions[cornerIndex].x) : 0 ,
                considerY ? (edgeOfTile.y - cornerPositions[cornerIndex].y) : 0);
    }

    public Vector2 getMoveCorrectionVector(Vector2 moveVector, PlayerPosition previousPlayerPosition) {
        cornerPositions = new Point[]{null, null, null, null};
        cornerTiles = new Tile[]{null, null, null, null};

        PlayerPosition playerPosition = previousPlayerPosition.calculateNewPosition(moveVector);

        calculateCornerData(moveVector, playerPosition);

        /*
            0 - 1
            |   |
            2 - 3
        */
        // check if 2 X or Y tiles are relevant
        boolean considerX = true, considerY = true;
        if(isTileRelevantForCollision(cornerTiles[Corner.topLeft.ordinal()]) && isTileRelevantForCollision(cornerTiles[Corner.topRight.ordinal()])
          || isTileRelevantForCollision(cornerTiles[Corner.bottomLeft.ordinal()]) && isTileRelevantForCollision(cornerTiles[Corner.bottomRight.ordinal()])) {
            considerX = false;
        }
        if(isTileRelevantForCollision(cornerTiles[Corner.topLeft.ordinal()]) && isTileRelevantForCollision(cornerTiles[Corner.bottomLeft.ordinal()])
                || isTileRelevantForCollision(cornerTiles[Corner.topRight.ordinal()]) && isTileRelevantForCollision(cornerTiles[Corner.bottomRight.ordinal()])) {
            considerY = false;
        }
        if(!considerX && !considerY){
            // diagonal movement
            considerX = true;
            considerY = true;
        }

        // when i move right and hit something on the right --> dont consider Y
        // when i move up and hit something up      --> dont consider X

        Gdx.app.log("MAZEGAME", "considerX: " + considerX);
        Gdx.app.log("MAZEGAME", "considerY: " + considerY);

        ArrayList<Vector2> correctionVectors = new ArrayList<>();
        for(int i = 0; i < Corner.values().length; i ++){
            if(cornerTiles[i] == null || cornerTiles[i].tile == null){
                continue;
            }

            if(cornerTiles[i].properties.containsKey("wall_collision")) {
                Vector2 v = calculateMoveCorrectionVector(playerPosition, i, considerX, considerY);

                Gdx.app.log("MAZEGAME", "correction [" + i + "]: " + v);

                correctionVectors.add(v);
            }
        }

        Vector2 accumulatedCorrectionVector = new Vector2(0, 0);
        for (Vector2 correctionVector:
             correctionVectors) {
            Vector2 correction = new Vector2(0,0);
            correction.x = Math.abs(accumulatedCorrectionVector.x) > Math.abs(correctionVector.x) ? accumulatedCorrectionVector.x : correctionVector.x;
            correction.y = Math.abs(accumulatedCorrectionVector.y) > Math.abs(correctionVector.y) ? accumulatedCorrectionVector.y : correctionVector.y;

            accumulatedCorrectionVector = correction;
        }

        return accumulatedCorrectionVector;

    }



    private Point[] calculateCornerPositions(Vector2 moveVector, PlayerPosition playerPosition) {
        Point[] cornerPositions = {null, null, null, null};

        /*
            0 - 1
            |   |
            2 - 3
        */

        if (moveVector.x < 0 || moveVector.y > 0){
            cornerPositions[Corner.topLeft.ordinal()]       = new Point(playerPosition.xMin, playerPosition.yMax); // topLeft
        }
        if (moveVector.x > 0 || moveVector.y > 0){
            cornerPositions[Corner.topRight.ordinal()]      = new Point(playerPosition.xMax, playerPosition.yMax); // topRight
        }
        if (moveVector.x < 0 || moveVector.y < 0) {
            cornerPositions[Corner.bottomLeft.ordinal()]    = new Point(playerPosition.xMin, playerPosition.yMin); // bottomLeft
        }
        if (moveVector.x > 0 || moveVector.y < 0) {
            cornerPositions[Corner.bottomRight.ordinal()]   = new Point(playerPosition.xMax, playerPosition.yMin); // bottomRight
        }

        Gdx.app.log("MazeGame", "xMin  " + playerPosition.xMin);
        Gdx.app.log("MazeGame", "xMax  " + playerPosition.xMax);
        Gdx.app.log("MazeGame", "yMin  " + playerPosition.yMin);
        Gdx.app.log("MazeGame", "yMax  " + playerPosition.yMax);



        Gdx.app.log("MazeGame", "topLeft" + ((cornerPositions[0] != null) ? cornerPositions[0].toString() : ""));
        Gdx.app.log("MazeGame", "topRight" + ((cornerPositions[1] != null) ? cornerPositions[1].toString() : ""));
        Gdx.app.log("MazeGame", "bottomLeft" + ((cornerPositions[2] != null) ? cornerPositions[2].toString() : ""));
        Gdx.app.log("MazeGame", "bottomRight" + ((cornerPositions[3] != null) ? cornerPositions[3].toString() : ""));

        return cornerPositions;
    }

    /*
       0 - 1
       |   |
       2 - 3
   */
    private Boolean isTileRelevantForCollision(Tile cornerTile){
        return cornerTile != null ;
        //&& cornerTile.properties.containsKey("wall_collision")
    }

}