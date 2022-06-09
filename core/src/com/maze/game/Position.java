package com.maze.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Position {
    public int xMin, xMax, yMin, yMax;

    public Position(int xMin, int yMin, int width, int height) {
        // x horizontal, y vertical
        this.xMin = xMin;
        this.xMax = xMin + width;
        this.yMin = yMin;
        this.yMax = yMin + height;
    }

    public Position update(Vector2 vector) {
        this.xMin += vector.x;
        this.xMax += vector.x;
        this.yMin += vector.y;
        this.yMax += vector.y;

        return this;
    }

    public Point getCenter(){
        return new Point((int)(xMin + 0.5 * xMax), (int)(yMin + 0.5 * yMax));
    }
}
