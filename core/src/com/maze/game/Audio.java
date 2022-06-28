package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Audio {
    private Audio(){}

    private static final String DIRECTORY = "assets/sound/";

    public static final Sound COLLECT_KEY_SOUND =     Gdx.audio.newSound(Gdx.files.internal(DIRECTORY + "collect_key.mp3"));
    public static final Sound STEP_ON_TRAP_SOUND =    Gdx.audio.newSound(Gdx.files.internal(DIRECTORY + "cat_steps_on_trap.mp3"));
    public static final Sound OPEN_DOOR_SOUND =       Gdx.audio.newSound(Gdx.files.internal(DIRECTORY + "open_door.mp3"));
    public static final Sound LEVEL_START_SOUND =     Gdx.audio.newSound(Gdx.files.internal(DIRECTORY + "level_start_meow.mp3"));
    public static final Sound LEVEL_FINISHED_SOUND =  Gdx.audio.newSound(Gdx.files.internal(DIRECTORY + "level_finished.mp3"));


    public static void playSound (Sound sound){;
        sound.play(1f);
    }

    public static void dispose(){
        COLLECT_KEY_SOUND.dispose();
        STEP_ON_TRAP_SOUND.dispose();
        OPEN_DOOR_SOUND.dispose();
        LEVEL_START_SOUND.dispose();
        LEVEL_FINISHED_SOUND.dispose();
    }
}
