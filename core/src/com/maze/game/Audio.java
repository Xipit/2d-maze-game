package com.maze.game;

import com.badlogic.gdx.audio.Sound;

/**
 * <h1>Audio</h1>
 * Statische Klasse (soweit es Java erlaubt) zur zentralen Referenz und Aufruf von Sound Dateien. <br/>
 * Gedacht zur einfachen Integration eines möglichen Lautstärkereglers oder anderen Einstellungen.
 *
 * @author  Hanno Witzleb, Jörn Drechsler
 */
public class Audio {
    private Audio(){}

    private static final String DIRECTORY = "assets/sound/";




    public static final Sound COLLECT_KEY_SOUND =     Assets.manager.get(Assets.COLLECT_KEY_SOUND, Sound.class);
    public static final Sound STEP_ON_TRAP_SOUND =    Assets.manager.get(Assets.STEP_ON_TRAP_SOUND, Sound.class);
    public static final Sound OPEN_DOOR_SOUND =       Assets.manager.get(Assets.OPEN_DOOR_SOUND, Sound.class);
    public static final Sound LEVEL_START_SOUND =     Assets.manager.get(Assets.LEVEL_START_SOUND, Sound.class);
    public static final Sound LEVEL_FINISHED_SOUND =  Assets.manager.get(Assets.LEVEL_FINISHED_SOUND, Sound.class);

    public static void playSound (Sound sound){;
        sound.play(1f);
    }
}
