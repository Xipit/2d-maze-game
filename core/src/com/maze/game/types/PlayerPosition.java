package com.maze.game.types;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class PlayerPosition {
    public int xMin, xMax, yMin, yMax;

    public PlayerPosition(int xMin, int yMin, int width, int height) {
        // x horizontal, y vertical
        this.xMin = xMin;
        this.xMax = xMin + width - 1;   // -1 so that xMax is inclusive
        this.yMin = yMin;
        this.yMax = yMin + height - 1;
    }
    public PlayerPosition(PlayerPosition position) {
        // x horizontal, y vertical
        this.xMin = position.xMin;
        this.xMax = position.xMax;
        this.yMin = position.yMin;
        this.yMax = position.yMax;
    }

    public PlayerPosition update(Vector2 vector) {
        this.xMin += vector.x;
        this.xMax += vector.x;
        this.yMin += vector.y;
        this.yMax += vector.y;

        return this;
    }

    public PlayerPosition calculateNewPosition(Vector2 vector) {
        PlayerPosition newPosition = new PlayerPosition(this);
        newPosition.update(vector);

        return newPosition;
    }

    public Point getCenter(){
        return new Point((int)(xMin + (0.5 * (xMax - xMin))), (int)(yMin + (0.5 * (yMax - yMin))));
    }
}
