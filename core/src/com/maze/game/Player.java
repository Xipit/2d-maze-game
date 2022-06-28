package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.maps.Level;
import com.maze.game.types.PlayerPosition;

import java.awt.*;
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

        if(!vector.isZero()){
            move(vector.scl(speed), level);

            checkForTriggers(level, levelScreen);
        }
    }

    private void move(Vector2 moveVector, Level level) {
        moveVector.scl(Gdx.graphics.getDeltaTime());
        moveVector = new Vector2(cleanNumber(moveVector.x), cleanNumber(moveVector.y));

        Vector2 moveCorrectionVector = level.getMoveCorrectionVector(moveVector, position);

        Vector2 correctedMoveVector = moveVector.add(moveCorrectionVector);
        this.position = position.update(correctedMoveVector);

    }

    private void checkForTriggers(Level level, LevelScreen levelScreen){
        level.checkForTriggers(this, levelScreen);
    }

    public void addKey(int keyType){
        if(heldKeys != null && !heldKeys.contains(keyType)){
            Gdx.app.log("MAZEGAME", "KEY ADDED with type: " + keyType);

            heldKeys.add(keyType);
        }
    }
    public boolean useKey(int keyType){
        if(heldKeys != null && heldKeys.contains(keyType)){
            Gdx.app.log("MAZEGAME", "KEY USED with type: " + keyType);

            heldKeys.remove((Object) keyType);
            return true;
        }
        return false;
    }

    public void disposeTextures() {
        texture.dispose();
    }


    private float cleanNumber (float number){
        return (float) (number > 0 ? Math.ceil(number) : Math.floor(number));
    }
}
