package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.maps.Map;
import com.maze.game.types.PlayerPosition;

import java.awt.*;

public class Player {
    public Texture texture;

    private final float speed = 200;
    private int width;
    private int height;
    public PlayerPosition position;

    public Player(Point startPosition){
        texture = Asset.manager.get("prototyp_cat.png", Texture.class);

        this.width = this.texture.getWidth();
        this.height = this.texture.getHeight();

        // create a Rectangle
        this.position = new PlayerPosition(startPosition.x, startPosition.y, this.width, this.height);
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

        vector.nor();  //normalize vector -> length = 1
        if(!vector.isZero()) move(vector.scl(speed), map);
    }

    public void move(Vector2 moveVector, Map map) {
        moveVector.scl(Gdx.graphics.getDeltaTime());
        moveVector = new Vector2((float)Math.ceil(moveVector.x), (float)Math.ceil(moveVector.y));

        Vector2 moveCorrectionVector = map.getMoveCorrectionVector(moveVector, position);

        Vector2 correctedMoveVector = moveVector.add(moveCorrectionVector);
        this.position = position.update(correctedMoveVector);

    }

    public void disposeTextures() {
        texture.dispose();
    }

}
