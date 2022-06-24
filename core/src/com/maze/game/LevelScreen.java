package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.maps.Map;
import com.maze.game.maps.PrototypeMap;

public class LevelScreen extends ScreenAdapter {
    private final MazeGameCamera camera;
    private final Map map;
    private final Player player;
    private final SpriteBatch sb;
    private final float zoomFactor = 1/4F;

    public LevelScreen() {
        camera = new MazeGameCamera(zoomFactor);

        map = new PrototypeMap();
        player = new Player(map.getStartingPoint());

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Player movement by keystroke
        player.input(map, this);

        // update Camera position
        camera.update(player.position.getCenter(), map.widthInPixel, map.heightInPixel);

        map.render(camera);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(player.texture, player.position.xMin, player.position.yMin);
        sb.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        // player.disposeTextures(); TODO remove: asset manager should maintain resources for the entire game session
    }
}
