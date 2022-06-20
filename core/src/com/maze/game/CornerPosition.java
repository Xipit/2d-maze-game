package com.maze.game;

import java.awt.*;

public class CornerPosition {
    public Point expected = null;
    public Point previous = null;


    public CornerPosition(Point expected, Point previous){
        this.expected = expected;
        this.previous = previous;
    }
}
