package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.types.PlayerPosition;

public class Player {
    public Texture texture;

    private float speed = 200;
    // todo INFO, clean png
    private int width = 50;
    private int height = 56;
    public PlayerPosition position;

    public Player(){
        //load images Katze_bearbeitet.png
        texture = new Texture(Gdx.files.internal("prototyp_cat.png"));

        // create a Rectangle
        this.position = new PlayerPosition(800 / 2 - width / 2, 64, width, height);
    }

    public void input(Map map) {
        Vector2 vector = new Vector2(0, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            vector.x += -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            vector.x += +1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            vector.y += -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            vector.y += +1;
        }

        vector.nor();  //normalize vector length = 1
        if(!vector.isZero()) move(vector.scl(speed), map);
    }

    public void move(Vector2 moveVector, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        moveVector.scl(deltaTime);

        moveVector = new Vector2((float)Math.ceil(moveVector.x), (float)Math.ceil(moveVector.y));

        Gdx.app.log("MazeGame", "---------------------------------------------");
        Gdx.app.log("MazeGame", "uncorrected moveVector: " + moveVector.toString());

        Vector2 correctedMoveVector = moveVector.add(map.getMoveCorrectionVector(moveVector, position));

        Gdx.app.log("MazeGame", "corrected moveVector: " + correctedMoveVector.toString());

        this.position = position.update(correctedMoveVector);
    }

    public void disposeTextures() {
        texture.dispose();
    }

}
