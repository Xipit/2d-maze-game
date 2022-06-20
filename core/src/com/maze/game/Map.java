package com.maze.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.types.Boolean2;
import com.maze.game.types.CornerPosition;
import com.maze.game.types.PlayerPosition;
import com.maze.game.types.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    static int WIDTH, HEIGHT;
    static int WIDTH_PIXEL, HEIGHT_PIXEL;
    static int TILE_WIDTH, TILE_HEIGHT;

    static String COL_KEY = "wall_collision";

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

    CornerPosition[] cornerPositions = {null, null, null, null};
    Tile[] cornerTiles = {null, null, null, null};

    private void resetData(){
        cornerPositions = new CornerPosition[]{null, null, null, null};
        cornerTiles = new Tile[]{null, null, null, null};
    }

    private Boolean2 calculateConsideration(int cornerIndex, Vector2 directionVector, Point previousCornerPosition, Point edgeOfTile){

        // for diagonal movements
        if(directionVectorToCorner(new Vector2(directionVector.x, directionVector.y * -1)) == cornerIndex){
            return new Boolean2(true, false);
        }
        else if(directionVectorToCorner(new Vector2(directionVector.x * -1, directionVector.y)) == cornerIndex){
            return new Boolean2(false, true);
        }

        Boolean2 checkIfEqual = new Boolean2(
                directionVector.x == 0 || directionVector.y == 0 || cornerPositions[cornerIndex].expected == edgeOfTile || Math.abs(previousCornerPosition.y - edgeOfTile.y) > Math.abs(previousCornerPosition.x - edgeOfTile.x),
                directionVector.x == 0 || directionVector.y == 0 || cornerPositions[cornerIndex].expected == edgeOfTile || Math.abs(previousCornerPosition.x - edgeOfTile.x) > Math.abs(previousCornerPosition.y - edgeOfTile.y));
        return new Boolean2(
                compareUsingDirectionVector((int)directionVector.x, checkIfEqual.x, previousCornerPosition.x, edgeOfTile.x),
                compareUsingDirectionVector((int)directionVector.y, checkIfEqual.y, previousCornerPosition.y, edgeOfTile.y)
        );


    }

    private int directionVectorToCorner(Vector2 directionVector){
        if(directionVector.y > 0 && directionVector.x < 0){
            return Corner.topLeft.ordinal();
        }
        else if(directionVector.y > 0 && directionVector.x > 0){
            return Corner.topRight.ordinal();
        }
        else if(directionVector.y < 0 && directionVector.x < 0){
            return Corner.bottomLeft.ordinal();
        }
        else if(directionVector.y < 0 && directionVector.x > 0){
            return Corner.bottomRight.ordinal();
        }
        return -1;
    }

    private boolean compareUsingDirectionVector(int decider, boolean checkIfEqual,  int leftHandInt, int rightHandInt){
        if(decider == 0) return false ;

        if(decider > 0 && checkIfEqual)     return leftHandInt <= rightHandInt;
        else if(decider > 0)                return leftHandInt < rightHandInt;

        if(decider < 0 && checkIfEqual)     return leftHandInt >= rightHandInt;
        else if(decider > 0)                return leftHandInt > rightHandInt;

        return false;
    }

    public void calculateCornerData(Vector2 directionVector, PlayerPosition playerPosition, PlayerPosition previousPlayerPosition) {
        cornerPositions = calculateCornerPositions(directionVector, playerPosition, previousPlayerPosition);

        for (int i = 0; i < Corner.values().length; i ++) {
            if(cornerPositions[i] == null) continue;

            Point cornerTileIndex = Tile.getIndex(cornerPositions[i].expected, TILE_WIDTH, TILE_HEIGHT);

            Tile cornerTile = new Tile(
                cornerTileIndex,
                this.tileLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile().getProperties(),
                this.tileLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile());

            cornerTiles[i] = cornerTile;
        }
    }

    private Point getEdgeOfTile(int cornerIndex, Point tilePosition){
        Point edgeOfTile = tilePosition; // bottomLeft of Tile

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

        return edgeOfTile;
    }

    private Vector2 calculateMoveCorrectionVector(Vector2 directionVector, int cornerIndex) {
        Point edgeOfTile = getEdgeOfTile(cornerIndex, cornerTiles[cornerIndex].getPosition(TILE_WIDTH, TILE_HEIGHT));

        Boolean2 cornerCollisionConsideration = calculateConsideration(cornerIndex, directionVector, cornerPositions[cornerIndex].previous, edgeOfTile);

        return new Vector2(
                cornerCollisionConsideration.x ? (edgeOfTile.x - cornerPositions[cornerIndex].expected.x) : 0 ,
                cornerCollisionConsideration.y ? (edgeOfTile.y - cornerPositions[cornerIndex].expected.y) : 0);
    }

    public Vector2 getMoveCorrectionVector(Vector2 moveVector, PlayerPosition previousPlayerPosition) {
        resetData();

        PlayerPosition playerPosition = previousPlayerPosition.calculateNewPosition(moveVector);

        calculateCornerData(moveVector, playerPosition, previousPlayerPosition);

        /*
            0 - 1
            |   |
            2 - 3
        */

        ArrayList<Vector2> correctionVectors = new ArrayList<>();
        for(int cornerIndex = 0; cornerIndex < Corner.values().length; cornerIndex ++){
            if(cornerTiles[cornerIndex] == null || cornerTiles[cornerIndex].tile == null){
                continue;
            }

            if(cornerTiles[cornerIndex].properties.containsKey(COL_KEY)) {
                Vector2 v = calculateMoveCorrectionVector(moveVector, cornerIndex);

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





    private CornerPosition[] calculateCornerPositions(Vector2 moveVector, PlayerPosition playerPosition, PlayerPosition previousPlayerPosition) {
        CornerPosition[] cornerPositions = {null, null, null, null};

        /*
            0 - 1
            |   |
            2 - 3
        */

        if (moveVector.x < 0 || moveVector.y > 0){
            cornerPositions[Corner.topLeft.ordinal()] = new CornerPosition(
                    new Point(playerPosition.xMin, playerPosition.yMax),
                    new Point(previousPlayerPosition.xMin, previousPlayerPosition.yMax)); // topLeft
        }
        if (moveVector.x > 0 || moveVector.y > 0){
            cornerPositions[Corner.topRight.ordinal()] = new CornerPosition(
                    new Point(playerPosition.xMax, playerPosition.yMax),
                    new Point(previousPlayerPosition.xMax, previousPlayerPosition.yMax)); // topRight
        }
        if (moveVector.x < 0 || moveVector.y < 0) {
            cornerPositions[Corner.bottomLeft.ordinal()] = new CornerPosition(
                    new Point(playerPosition.xMin, playerPosition.yMin),
                    new Point(previousPlayerPosition.xMin, previousPlayerPosition.yMin)); // bottomLeft
        }
        if (moveVector.x > 0 || moveVector.y < 0) {
            cornerPositions[Corner.bottomRight.ordinal()] = new CornerPosition(
                    new Point(playerPosition.xMax, playerPosition.yMin),
                    new Point(previousPlayerPosition.xMax, previousPlayerPosition.yMin)); // bottomRight
        }

        return cornerPositions;
    }

}