package com.maze.game.types;

import java.awt.*;

public class Key extends Object{
    public int keyType;
    public Point tileIndexOfkey;

    public Key(int keyType, Point tileIndexOfkey){
        this.keyType = keyType;
        this.tileIndexOfkey = tileIndexOfkey;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Key)
            return (this.tileIndexOfkey == ((Key) obj).tileIndexOfkey);

        return (false);
    }

    @Override
    public native int hashCode();
}
