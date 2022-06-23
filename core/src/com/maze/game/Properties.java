package com.maze.game;

public class Properties {
    private Properties(){}

    /*
     *      BASE LAYER
     */
    public static final String COLLISION_KEY = "wall_collision";

    public static final String DOOR_DIRECTION_KEY = "door_direction";
    public enum DoorDirection{
        N, S, W, E
    } // use with Properties.DoorDirection.value()


    public static final String DOOR_STATUS_KEY = "door_status";
    // 0 -> opened, 1-> closed

    public static final String DOOR_TYPE_KEY = "door_type";
    // 0, 1, 2 -> corresponds to KEY_TYPE

    public static final String VICTORY_KEY = "victory";

    public static final String TRAP_KEY = "trap";


    /*
     *      INTERACTION LAYER
     */

    public static final String KEY_STATUS_KEY = "key_status";
    // 0 -> taken, 1-> still there

    public static final String KEY_TYPE_KEY = "key_type";
    // 0, 1, 2 -> corresponds to DOOR_TYPE

}
