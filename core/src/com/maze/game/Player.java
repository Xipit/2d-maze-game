package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.maps.Map;
import com.maze.game.types.PlayerPosition;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public Texture texture;

    private final float speed = 200;
    private final int width;
    private final int height;
    public PlayerPosition position;

    private List<Integer> heldKeys = new ArrayList<Integer>();


    public Player(Point startPosition){
        texture = Assets.manager.get("prototyp_cat_32.png", Texture.class);

        this.width = this.texture.getWidth();
        this.height = this.texture.getHeight();

        // create a Rectangle
        this.position = new PlayerPosition(startPosition.x, startPosition.y, this.width, this.height);
    }

    public void input(Map map, LevelScreen levelScreen) {
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

        if(!vector.isZero()){
            move(vector.scl(speed), map);

            checkForTriggers(map, levelScreen);
        }
    }

    private void move(Vector2 moveVector, Map map) {
        moveVector.scl(Gdx.graphics.getDeltaTime());
        moveVector = new Vector2((float)Math.ceil(moveVector.x), (float)Math.ceil(moveVector.y));

        Vector2 moveCorrectionVector = map.getMoveCorrectionVector(moveVector, position);

        Vector2 correctedMoveVector = moveVector.add(moveCorrectionVector);
        this.position = position.update(correctedMoveVector);

    }

    private void checkForTriggers(Map map, LevelScreen levelScreen){
        map.checkForTriggers(this, levelScreen);
    }

    public void addKey(int keyType){
        if(heldKeys != null && !heldKeys.contains(keyType)){
            heldKeys.add(keyType);
        }
    }
    public boolean useKey(int keyType){
        if(heldKeys != null && heldKeys.contains(keyType)){
            heldKeys.remove(keyType);
            return true;
        }
        return false;
    }

    public void disposeTextures() {
        texture.dispose();
    }

}
