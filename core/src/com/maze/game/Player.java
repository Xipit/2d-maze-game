package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Player {
    public Texture texture;

    public Rectangle shape;

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
        shape = new Rectangle();
        shape.x = this.position.xMin;
        shape.y = this.position.yMin;
        shape.width = width;
        shape.height = height;
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

        // Map.accountForCollision needs vector * speed as moveVector

        // if !LevelScreen.currentMap.getWallCollision(shape.x, (int) (shape.y + speed * deltaTime),this);


        // respectBoundaries();
    }

    private void respectBoundaries() {
        // todo CHANGED
        if(shape.x < 0) shape.x = 0;
        if(shape.x > Map.WIDTH_PIXEL - width) shape.x =  Map.WIDTH_PIXEL - width;

        if(shape.x > Map.WIDTH_PIXEL - width) shape.x =  Map.WIDTH_PIXEL - width;
        if(shape.y < 0) shape.y = 0;
        if(shape.y > Map.HEIGHT_PIXEL - height) shape.y =  Map.HEIGHT_PIXEL - height;

    }

    public void move(Vector2 moveVector, Map map) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        moveVector.scl(deltaTime);
        moveVector = new Vector2((float)Math.floor(moveVector.x), (float)Math.floor(moveVector.y));

        Gdx.app.log("MazeGame", "---------------------------------------------");


        Gdx.app.log("MazeGame", "uncorrected moveVector: " + moveVector.toString());

        Vector2 correctedMoveVector = moveVector.add(map.accountForCollision(moveVector, position));

        Gdx.app.log("MazeGame", "corrected moveVector: " + correctedMoveVector.toString());

        this.position = position.update(correctedMoveVector);

        shape.x += correctedMoveVector.x;
        shape.y += correctedMoveVector.y;
    }

    public void disposeTextures() {
        texture.dispose();
    }

}
