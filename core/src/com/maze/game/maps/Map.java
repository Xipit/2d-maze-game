package com.maze.game.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.*;
import com.maze.game.types.*;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    public final int width, height;
    public final int widthInPixel, heightInPixel;
    public final int tileWidthInPixel, tileHeightInPixel;

    private final TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer renderer;
    protected final TiledMapTileLayer baseLayer;
    protected final TiledMapTileLayer interactionLayer;

    private CornerPosition[] cornerPositions;
    private Tile[] cornerTiles;
    private Vector2 moveCorrectionVector;

    public Map(String fileName) {
        this.tiledMap = Assets.manager.get(fileName, TiledMap.class);
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        this.baseLayer = (TiledMapTileLayer) this.tiledMap.getLayers().get("base");
        this.interactionLayer = (TiledMapTileLayer) this.tiledMap.getLayers().get("interaction");

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

            LayerTile baseCornerTile = new LayerTile(
                    cornerTileIndex,
                    this.baseLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile().getProperties(),
                    this.baseLayer.getCell(cornerTileIndex.x, cornerTileIndex.y).getTile(),
                    cornerIndex);

            // The base layer always has tiles associated with the cells, but this does not apply to the interaction layer.
            LayerTile interactionCornerTile;
            TiledMapTileLayer.Cell interactionCornerCell;
            if ((interactionCornerCell = this.interactionLayer.getCell(cornerTileIndex.x, cornerTileIndex.y)) != null) {
                interactionCornerTile = new LayerTile(
                        cornerTileIndex,
                        interactionCornerCell.getTile().getProperties(),
                        interactionCornerCell.getTile(),
                        cornerIndex);
            }
            else {
                interactionCornerTile = null;
            }

            cornerTiles[cornerIndex] = new Tile(baseCornerTile, interactionCornerTile);
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
        Point edgeOfTile = cornerTiles[cornerIndex].base.getCollisionEdge(tileWidthInPixel, tileHeightInPixel);

        Boolean2 cornerCollisionConsideration = calculateCollisionConsideration(cornerIndex, directionVector, cornerPositions[cornerIndex].previous, edgeOfTile);

        return new Vector2(
                cornerCollisionConsideration.x ? (edgeOfTile.x - cornerPositions[cornerIndex].expected.x) : 0 ,
                cornerCollisionConsideration.y ? (edgeOfTile.y - cornerPositions[cornerIndex].expected.y) : 0);
    }

    public Vector2 getMoveCorrectionVector(Vector2 moveVector, PlayerPosition previousPlayerPosition) {
        calculateCollisionData(moveVector, previousPlayerPosition);

        ArrayList<Vector2> correctionVectors = new ArrayList<>();
        for(int cornerIndex = 0; cornerIndex < Corner.values().length; cornerIndex ++){
            if(cornerTiles[cornerIndex] == null || cornerTiles[cornerIndex].base.tile == null){
                continue;
            }

            MapProperties baseProperties = cornerTiles[cornerIndex].base.properties;

            if(baseProperties.containsKey(Properties.COLLISION_KEY) ||
                    (baseProperties.containsKey(Properties.DOOR_DIRECTION_KEY) && baseProperties.containsKey(Properties.DOOR_STATUS_KEY) && baseProperties.containsKey(Properties.DOOR_TYPE_KEY))) {
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

    public void checkForTriggers(PlayerPosition currentPlayerPosition, LevelScreen levelScreen){
        if(!moveCorrectionVector.isZero()){
            calculateCollisionData(currentPlayerPosition);
        }


        for(int cornerIndex = 0; cornerIndex < Corner.values().length; cornerIndex ++){
            if(cornerTiles[cornerIndex] == null){
                continue;
            }


            if(cornerTiles[cornerIndex].base.tile != null){
                MapProperties baseProperties = cornerTiles[cornerIndex].base.properties;

                if(baseProperties.containsKey(Properties.DOOR_DIRECTION_KEY) && baseProperties.containsKey(Properties.DOOR_STATUS_KEY) && baseProperties.containsKey(Properties.DOOR_TYPE_KEY)){
                    // TODO open door if have key, change texture
                }
                if(baseProperties.containsKey(Properties.TRAP_KEY)){
                    // TODO die
                    levelScreen.dispose();
                    MazeGame.instance.setScreen(new LevelScreen());
                    return;
                }
                if(baseProperties.containsKey(Properties.VICTORY_KEY)){
                    // TODO win game
                    levelScreen.dispose();
                    MazeGame.instance.setScreen(new VictoryScreen());
                    return;
                }
            }

            if(cornerTiles[cornerIndex].interaction != null && cornerTiles[cornerIndex].interaction.tile != null){
                MapProperties interactionProperties = cornerTiles[cornerIndex].interaction.properties;

                if(interactionProperties.containsKey(Properties.KEY_TYPE_KEY) && interactionProperties.containsKey(Properties.KEY_STATUS_KEY)) {
                    // TODO collect key, change texture
                }
            }
        }

    }

    public void dispose() {
        this.tiledMap.dispose();
        // this.renderer.dispose(); TODO crash
        this.resetData();
    }
}