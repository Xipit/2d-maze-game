package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelScreen extends ScreenAdapter {
    static Map currentMap;

    private OrthographicCamera camera;
    private Player player;
    private SpriteBatch sb;

    public LevelScreen(String tilemapFile) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        currentMap = new Map(tilemapFile, 1f);
        player = new Player();

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();

        // todo Zentriere den Player, es sei denn, der Abstand zu dem Rand ist nicht groß genug oder Umgebung art render?
        camera.position.set((float) player.position.getCenter().x, (float)player.position.getCenter().y, 0);
        currentMap.render(camera);
        // erste Frame(s) nicht position gewechselt
        sb.setProjectionMatrix(camera.combined);

        // new batch sb
        sb.begin();
        sb.draw(player.texture, player.shape.x, player.shape.y);
        sb.end();

        // Player movement by keystroke
        player.input();
    }

    @Override
    public void dispose() {
        currentMap.dispose();
    }
}