package com.maze.game.types;

import java.awt.*;
/**
 * <h1>Eck Position</h1>
 * Datentyp speziell für die Eck Positionen bei der Kollisionsberechnung.
 *
 * @author  Hanno Witzleb, Jörn Drechsler
 */
public class CornerPosition {
    public Point expected = null;
    public Point previous = null;


    public CornerPosition(Point expected, Point previous){
        this.expected = expected;
        this.previous = previous;
    }
}
