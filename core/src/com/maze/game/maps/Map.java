package com.maze.game.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.Assets;
import com.maze.game.types.Boolean2;
import com.maze.game.types.CornerPosition;
import com.maze.game.types.PlayerPosition;
import com.maze.game.types.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    public final int width, height;
    public final int widthInPixel, heightInPixel;
    public final int tileWidthInPixel, tileHeightInPixel;

    static String COL_KEY = "wall_collision";

    private final TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer renderer;
    protected final TiledMapTileLayer tileLayer;

    private CornerPosition[] cornerPositions;
    private Tile[] cornerTiles;
    private Vector2 moveCorrectionVector;

    public Map(String fileName) {
        this.tiledMap = Assets.manager.get(fileName, TiledMap.class);
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        this.tileLayer = (TiledMapTileLayer) this.tiledMap.getLayers().get("tiles");

        width = this.tiledMap.getProperties().get("width", Integer.class);
        height = this.tiledMap.getProperties().get("height", Integer.class);
        tileWidthInPixel = this.tiledMap.getProperties().get("tilewidth", Integer.class);
        tileHeightInPixel = this.tiledMap.getProperties().get("tileheight", Integer.class);
        widthInPixel = width * tileWidthInPixel;
        heightInPixel = height * tileHeightInPixel;
    }

    public void render(OrthographicCamera camera) {
        this.renderer.setMap(this.tiledMap);
        this.renderer.setView(camera);
        this.renderer.render();
    }

    protected String getCollisionKey(){
        return COL_KEY;
    }

    public Point getStartingPoint(){
        return new Point(tileWidthInPixel, tileHeightInPixel);
    }

    /*
       0 - 1       topLeft     -   topRight
       |   |       |                      |
       2 - 3       bottomLeft - bottomRight
   */
    public enum Corner{
        topLeft,        topRight,

        bottomLeft,     bottomRight
    }


    //region Data-calculation
    private void resetData(){
        cornerPositions = new CornerPosition[]{null, null, null, null};
        cornerTiles = new Tile[]{null, null, null, null};
        moveCorrectionVector = new Vector2(0,0);
    }

    private void calculateCollisionData(PlayerPosition currenPlayerPosition){
        calculateCollisionData(new Vector2(0,0), currenPlayerPosition);
    }

    private void calculateCollisionData(Vector2 moveVector, PlayerPosition previousPlayerPosition) {
        resetData();

        cornerPositions = calculateCornerPositions(
                moveVector,
                adjustForBorderViolation(previousPlayerPosition.calculateNewPosition(moveVector)),
                previousPlayerPosition);


        for (int cornerIndex = 0; cornerIndex < Corner.values().length; cornerIndex ++) {
            if(cornerPositions[cornerIndex] == null) continue;

            Point cornerTileIndex = Tile.getIndex(cornerPositions[cornerIndex].expected, tileWidthInPixel, tileHeightInPixel);

            Tile cornerTile = new Tile(
                    cornerTileIndex,
                    this.tileLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile().getProperties(),
                    this.tileLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile(),
                    cornerIndex);

            cornerTiles[cornerIndex] = cornerTile;
        }
    }

    private PlayerPosition adjustForBorderViolation(PlayerPosition playerPosition) {
        if (playerPosition.xMin  < 0) {
            moveCorrectionVector.x = -playerPosition.xMin;
            playerPosition.update(new Vector2(-playerPosition.xMin, 0));
        }
        else if (playerPosition.xMax >= widthInPixel) {
            moveCorrectionVector.x = -(playerPosition.xMax - widthInPixel) - 1;
            playerPosition.update(new Vector2(-(playerPosition.xMax - widthInPixel) - 1, 0));
        }
        if (playerPosition.yMin < 0) {
            moveCorrectionVector.y = -playerPosition.yMin;
            playerPosition.update(new Vector2(0, -playerPosition.yMin));

        }
        else if (playerPosition.yMax >= heightInPixel) {
            moveCorrectionVector.y = -(playerPosition.yMax - heightInPixel) - 1;
            playerPosition.update(new Vector2(0, -(playerPosition.yMax - heightInPixel) - 1));
        }
        return playerPosition;
    }

    private CornerPosition[] calculateCornerPositions(Vector2 moveVector, PlayerPosition playerPosition, PlayerPosition previousPlayerPosition) {
        CornerPosition[] cornerPositions = {null, null, null, null};

        /*
            0 - 1       topLeft     -   topRight
            |   |       |                      |
            2 - 3       bottomLeft - bottomRight
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
    //endregion

    //region Collision-consideration
    private Boolean2 calculateCollisionConsideration(int cornerIndex, Vector2 directionVector, Point previousCornerPosition, Point edgeOfTile){

        // for diagonal movements where the corners are not leading (e.g. Moving towards topLeft -> topLeft leads, while topRight & bottomLeft can still collide)
        if(diagonalDirectionVectorToCorner(new Vector2(directionVector.x, directionVector.y * -1)) == cornerIndex){
            return new Boolean2(true, false);
        }
        else if(diagonalDirectionVectorToCorner(new Vector2(directionVector.x * -1, directionVector.y)) == cornerIndex){
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

    private int diagonalDirectionVectorToCorner(Vector2 directionVector){
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
    //endregion

    private Vector2 calculateMoveCorrectionVector(Vector2 directionVector, int cornerIndex) {
        Point edgeOfTile = cornerTiles[cornerIndex].getCollisionEdge(tileWidthInPixel, tileHeightInPixel);

        Boolean2 cornerCollisionConsideration = calculateCollisionConsideration(cornerIndex, directionVector, cornerPositions[cornerIndex].previous, edgeOfTile);

        return new Vector2(
                cornerCollisionConsideration.x ? (edgeOfTile.x - cornerPositions[cornerIndex].expected.x) : 0 ,
                cornerCollisionConsideration.y ? (edgeOfTile.y - cornerPositions[cornerIndex].expected.y) : 0);
    }

    public Vector2 getMoveCorrectionVector(Vector2 moveVector, PlayerPosition previousPlayerPosition) {
        calculateCollisionData(moveVector, previousPlayerPosition);

        ArrayList<Vector2> correctionVectors = new ArrayList<>();
        for(int cornerIndex = 0; cornerIndex < Corner.values().length; cornerIndex ++){
            if(cornerTiles[cornerIndex] == null || cornerTiles[cornerIndex].tile == null){
                continue;
            }

            if(cornerTiles[cornerIndex].properties.containsKey(getCollisionKey())) {
                Vector2 cornerCorrectionVector = calculateMoveCorrectionVector(moveVector, cornerIndex);

                correctionVectors.add(cornerCorrectionVector);
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

        moveCorrectionVector.add(accumulatedCorrectionVector);

        return moveCorrectionVector;
    }

    public void checkForTriggers(PlayerPosition currentPlayerPosition){
        String KEY_KEY = "", DOOR_KEY = "", TRAP_KEY = ""; //TODO: implement similar to colission key

        if(!moveCorrectionVector.isZero()){
            calculateCollisionData(currentPlayerPosition);
        }


        for(int cornerIndex = 0; cornerIndex < Corner.values().length; cornerIndex ++){
            if(cornerTiles[cornerIndex] == null || cornerTiles[cornerIndex].tile == null){
                continue;
            }

            if(cornerTiles[cornerIndex].properties.containsKey(KEY_KEY)) {
                // TODO collect key
            }
            if(cornerTiles[cornerIndex].properties.containsKey(DOOR_KEY)){
                // TODO open door if have key
            }
            if(cornerTiles[cornerIndex].properties.containsKey(TRAP_KEY)){
                // TODO die
            }
        }

    }

    public void dispose() {
        this.tiledMap.dispose();
        this.renderer.dispose();
    }

}