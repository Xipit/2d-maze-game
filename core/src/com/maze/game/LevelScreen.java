package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.maps.Map;
import com.maze.game.maps.PrototypeMap;

public class LevelScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final Map map;
    private final Player player;
    private final SpriteBatch sb;
    private final float zoomFactor = 0.6f;

    public LevelScreen() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        map = new PrototypeMap();
        player = new Player();

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Player movement by keystroke
        player.input(map);

        // Center the player, unless the distance to the edge is not large enough to display only the map.
        camera.position.set(
                (player.position.getCenter().x < Gdx.graphics.getWidth() * zoomFactor / 2) ? (float) Gdx.graphics.getWidth() * zoomFactor / 2 : Math.min(player.position.getCenter().x, (Map.getWidthPixel() - (float) Gdx.graphics.getWidth() * zoomFactor / 2)),
                (player.position.getCenter().y < Gdx.graphics.getHeight() * zoomFactor / 2) ? (float) Gdx.graphics.getHeight() * zoomFactor / 2 : Math.min(player.position.getCenter().y, (Map.getHeightPixel() - (float) Gdx.graphics.getHeight() * zoomFactor / 2)),
                0);
        camera.zoom = zoomFactor;
        camera.update();

        map.render(camera);
        // erste Frame(s) nicht position gewechselt
        sb.setProjectionMatrix(camera.combined);

        // new batch sb
        sb.begin();
        sb.draw(player.texture, player.position.xMin, player.position.yMin);
        sb.end();


    }

    @Override
    public void dispose() {
        map.dispose();
        player.disposeTextures();
    }
}
