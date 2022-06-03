package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Player {
    public static Texture texture;

    public static Rectangle shape;

    private float speed = 200;
    private int width = 64;
    private int height = 64;

    public Player(){
        //load images Katze_bearbeitet.png
        texture = new Texture(Gdx.files.internal("Katze_bearbeitet.png"));

        // create a Rectangle
        shape = new Rectangle();
        shape.x = 800 / 2 - width / 2;
        shape.y = 20;
        shape.width = width;
        shape.height = height;
    }

    public void movement() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            shape.x -= speed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            shape.x += speed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            shape.y += speed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            shape.y -= speed * deltaTime;
        }

        respectBoundaries();
    }

    private void respectBoundaries() {
        if(shape.x < 0) shape.x = 0;
        if(shape.x > Gdx.graphics.getWidth() - width) shape.x =  Gdx.graphics.getWidth() - width;
        if(shape.y < 0) shape.y = 0;
        if(shape.y > Gdx.graphics.getHeight() - height) shape.y =  Gdx.graphics.getHeight() - height;

    }

    public void disposeTextures() {
        texture.dispose();
    }

}
