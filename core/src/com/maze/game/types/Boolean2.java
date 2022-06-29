package com.maze.game.types;

/**
 * <h1>2D Boolean</h1>
 * Nützlich für die Übersichtlichkeit von 2D Vektor Konditionen.
 *
 * @author  Hanno Witzleb
 */
public class Boolean2 {
    public boolean x;
    public boolean y;

    public Boolean2(boolean x, boolean y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "[" + x + "|" + y + "]";
    }
}
