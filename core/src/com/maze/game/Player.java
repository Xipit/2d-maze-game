package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Player {
    Texture texture;
    Rectangle shape;
    Position position;

    private float speed = 200;
    private int width = 64;
    private int height = 64;

    public Player(){
        //load images Katze_bearbeitet.png
        texture = new Texture(Gdx.files.internal("prototyp_cat.png"));

        // create a Rectangle
        this.position = new Position(65, 65, width, height);
        shape = new Rectangle();
        shape.x = this.position.xMin;
        shape.y = this.position.yMin;
        shape.width = width;
        shape.height = height;
    }

    public void input() {
        Vector2 vector = new Vector2(0, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            vector.x = -1;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            vector.x = +1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            vector.y = -1;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            vector.y = +1;
        }
        vector.nor();  //normalize vector length = 1
        if(!vector.isZero()) move(vector.scl(speed));
    }

    public void move(Vector2 moveVector) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        moveVector.scl(deltaTime);
        // the movement vector should be integer, Position holds only integer
        // todo : maybe not moving with every frame, but n times per second, independent of the fps and more accurate than to round
        moveVector = new Vector2(Math.round(moveVector.x), Math.round(moveVector.y));

        Gdx.app.log("MazeGame", "uncorrected moveVector: " + moveVector.toString());

        moveVector.add(LevelScreen.currentMap.accountForCollision(moveVector, this.position));

        Gdx.app.log("MazeGame", "corrected moveVector: " + moveVector.toString());

        this.position.update(moveVector);

        this.shape.x += moveVector.x;
        this.shape.y += moveVector.y;
    }

    public void disposeTextures() {
        texture.dispose();
    }

}
