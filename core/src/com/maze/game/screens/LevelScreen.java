package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.Assets;
import com.maze.game.MazeGameCamera;
import com.maze.game.Player;
import com.maze.game.maps.Level;
import com.maze.game.maps.PrototypeLevel;

/**
 * <h1>LevelScreen</h1>
 * Verantwortlich um alle notwendigen Objekte für ein Level zu instanziieren und für den Aufruf von GameLogik (Input, usw.) für jeden Frame.<br/>
 * Schnittstelle zwischen Gameplay und UI.
 *
 * @author  Jörn Drechsler, Hanno Witzleb, Lucas Neugebauer
 */
public class LevelScreen extends ScreenAdapter {
    private final MazeGameCamera camera;
    private final Level level;
    private final Player player;
    private final SpriteBatch sb;
    private final float zoomFactor = 1/4F;

    public LevelScreen() {
        camera = new MazeGameCamera(zoomFactor);

        Assets.loadTileMap(PrototypeLevel.TILEMAP_FILENAME);
        level = new PrototypeLevel();

        player = new Player(level);

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Player movement by keystroke
        player.input(level, this);

        // update Camera position
        camera.update(player.position.getCenter(), level.widthInPixel, level.heightInPixel);

        level.render(camera);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(player.texture, player.position.xMin, player.position.yMin);
        sb.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        level.dispose();
    }
}
