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
    private int width;
    private int height;
    public PlayerPosition position;

    public Player(){
        this.texture = new Texture(Gdx.files.internal("prototyp_cat.png"));

        this.width = this.texture.getWidth();
        this.height = this.texture.getHeight();
        this.position = new PlayerPosition(Map.TILE_WIDTH, Map.TILE_HEIGHT, this.width, this.height);
        this.shape = new Rectangle();
        this.shape.x = this.position.xMin;
        this.shape.y = this.position.yMin;
        this.shape.width = this.width;
        this.shape.height = this.height;
    }

    public void input(Map map) {
        Vector2 vector = new Vector2(0, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            vector.x += -1;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            vector.x += +1;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            vector.y += -1;
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            vector.y += +1;

        vector.nor();  //normalize vector length = 1
        if(!vector.isZero()) move(vector.scl(speed), map);

        // Map.accountForCollision needs vector * speed as moveVector
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

        shape.x += correctedMoveVector.x;
        shape.y += correctedMoveVector.y;
    }

    public void disposeTextures() {
        texture.dispose();
    }
}
