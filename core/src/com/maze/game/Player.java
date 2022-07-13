package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.levels.Level;
import com.maze.game.levels.LevelData;
import com.maze.game.screens.LevelScreen;
import com.maze.game.types.Key;
import com.maze.game.types.PlayerPosition;
import com.maze.game.types.PlayerRenderData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Spieler</h1>
 * Repräsentiert die Spielfigur.<br/>
 * Verantwortlich für:<br/>
 *  - Position<br/>
 *  - Rendering<br/>
 *  - Input<br/>
 *  - Bewegung<br/>
 *
 * @author  Hanno Witzleb, Jörn Drechsler, Lilia Schneider
 */
public class Player {
    public PlayerRenderData renderData;

    private final float speed = 170;
    public int width = 28;
    public int height = 28;
    public PlayerPosition position;

    public List<Key> heldKeys = new ArrayList<Key>();
    public List<Point> openedDoorIndices = new ArrayList<Point>();

    private final LevelData levelData;
    public Boolean allowMovement = true;


    public Player(Level level){
        this.levelData = level.levelData;
        this.renderData = new PlayerRenderData(this.width, this.height);

        Point startPosition = level.getStartingPoint(this.height);
        this.position = new PlayerPosition(startPosition.x, startPosition.y, this.width, this.height);
    }

    public void input(Level level, LevelScreen levelScreen) {
        Vector2 vector = new Vector2(0, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            vector.x += -1;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            vector.x += +1;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            vector.y += -1;
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            vector.y += +1;

        vector.nor();  //normalize vector -> length = 1

        if(!vector.isZero() && allowMovement){
            move(vector.scl(speed), level);

            checkForTriggers(level, levelScreen);
        }

        if(allowMovement) this.renderData.update(vector, this.position);
        else this.renderData.sprite.setTexture(Assets.manager.get(Assets.CAT_SITTING_LEFT));
    }

    private void move(Vector2 moveVector, Level level) {
        moveVector.scl(Gdx.graphics.getDeltaTime());
        moveVector = new Vector2(cleanNumber(moveVector.x), cleanNumber(moveVector.y));

        Vector2 moveCorrectionVector = level.getMoveCorrectionVector(moveVector, position);
        Vector2 correctedMoveVector = moveVector.add(moveCorrectionVector);

        this.position.update(correctedMoveVector);
    }

    private void checkForTriggers(Level level, LevelScreen levelScreen){
        level.checkForTriggers(this, levelScreen, levelData);
    }

    public void addKey(int keyType, Point tileIndex){
        if(heldKeys != null && !containsKey(tileIndex)){

            heldKeys.add(new Key(keyType, tileIndex));
        }
    }
    public boolean useKey(int keyType, Point tileIndex){
        if(heldKeys != null && containsKeyType(keyType) && !openedDoorIndices.contains(tileIndex)){

            openedDoorIndices.add(tileIndex);
            removeOnly1Key(keyType);
            return true;
        }
        return false;
    }

    private Boolean containsKeyType(int keyType){
        for (Key key :
                heldKeys) {
            if(key != null && (key.keyType == keyType)){
                return true;
            }
        }
        return false;
    }
    private Boolean containsKey(Point tileIndex){
        for (Key key :
                heldKeys) {
            if(key != null && (key.tileIndexOfkey.x == tileIndex.x && key.tileIndexOfkey.y == tileIndex.y)){
                return true;
            }
        }
        return false;
    }

    private boolean removeOnly1Key(int keyTypeToRemove){
        for (int i = 0; i < heldKeys.size(); i++) {
            if(keyTypeToRemove == heldKeys.get(i).keyType){
                heldKeys.remove(i);
                return true;
            }
        }
        return false;
    }


    private float cleanNumber (float number){
        return (float) (number > 0 ? Math.ceil(number) : Math.floor(number));
    }


}
