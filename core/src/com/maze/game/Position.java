package com.maze.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Position {
    public int xMin, xMax, yMin, yMax;

    public Position(int xMin, int yMin, int width, int height) {
        // x horizontal, y vertical
        this.xMin = xMin;
        this.xMax = xMin + width - 1;
        this.yMin = yMin;
        this.yMax = yMin + height - 1;
    }

    public Position(Position position) {
        this.xMin = position.xMin;
        this.xMax = position.xMax;
        this.yMin = position.yMin;
        this.yMax = position.yMax;
    }

    public Position update(Vector2 vector) {
        this.xMin += vector.x;
        this.xMax += vector.x;
        this.yMin += vector.y;
        this.yMax += vector.y;

        return this;
    }

    public Position calculateNewPosition(Vector2 vector) {
        Position newPosition = new Position(this);
        newPosition.update(vector);

        return newPosition;
    }

    public Point getCenter(){
        return new Point((int)(this.xMin + 0.5 * (this.xMax - this.xMin)), (int)(this.yMin + 0.5 * (this.yMax - this.yMin)));
    }
}
