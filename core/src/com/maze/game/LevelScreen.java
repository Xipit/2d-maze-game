package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final Map currentMap;

    private Player player;

    private SpriteBatch sb;
    private float zoomFactor = 0.5f;

    public LevelScreen(String tilemapFile) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        currentMap = new Map(tilemapFile);
        player = new Player();

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Player movement by keystroke
        player.input(currentMap);

        // Center the player, unless the distance to the edge is not large enough to display only the map.
        camera.position.set(
                (player.position.getCenter().x < Gdx.graphics.getWidth() * zoomFactor / 2) ? (float) Gdx.graphics.getWidth() * zoomFactor / 2 : Math.min(player.position.getCenter().x, (Map.WIDTH_PIXEL - (float) Gdx.graphics.getWidth() * zoomFactor / 2)),
                (player.position.getCenter().y < Gdx.graphics.getHeight() * zoomFactor / 2) ? (float) Gdx.graphics.getHeight() * zoomFactor / 2 : Math.min(player.position.getCenter().y, (Map.HEIGHT_PIXEL - (float) Gdx.graphics.getHeight() * zoomFactor / 2)),
                0);
        camera.zoom = zoomFactor;
        camera.update();

        currentMap.render(camera);
        // erste Frame(s) nicht position gewechselt
        sb.setProjectionMatrix(camera.combined);

        // new batch sb
        sb.begin();
        sb.draw(player.texture, player.shape.x, player.shape.y);
        sb.end();
    }

    @Override
    public void dispose() {
        currentMap.dispose();
    }
}
