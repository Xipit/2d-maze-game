package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.Assets;
import com.maze.game.MazeGame;
import com.maze.game.MazeGameCamera;
import com.maze.game.Player;
import com.maze.game.levels.*;


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
    private final SpriteBatch levelSpriteBatch;
    private final SpriteBatch UISpriteBatch;
    private final float zoomFactor = 1/4F;

    private final Texture[] keyTextures = new Texture[3];

    public LevelScreen(LevelData levelData){
        camera = new MazeGameCamera(zoomFactor);


        Assets.loadTileMap(levelData.getFileName());
        this.level = new Level(levelData);

        for (int keyIndex = 0; keyIndex < Assets.KEYS.length; keyIndex++) {
            keyTextures[keyIndex] = Assets.manager.get(Assets.KEYS[keyIndex]);
        }

        player = new Player(this.level);

        levelSpriteBatch = new SpriteBatch();
        UISpriteBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Player movement by keystroke
        player.input(level, this);

        this.input();

        // update Camera position
        camera.update(player.position.getCenter(), level.widthInPixel, level.heightInPixel);

        level.render(camera);

        levelSpriteBatch.setProjectionMatrix(camera.combined);
        levelSpriteBatch.begin();

        player.renderData.sprite.draw(levelSpriteBatch);

        levelSpriteBatch.end();


        UISpriteBatch.begin();

        for (int i = 0; i < player.heldKeys.size(); i++) {
            drawHeldKey(keyTextures[player.heldKeys.get(i).keyType], i);
        }

        UISpriteBatch.end();
    }

    public void input(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){

            MazeGame.instance.setScreen(new LevelSelectScreen(level.levelData.findIndex()));
        }
    }

    public void drawHeldKey(Texture texture, int keyIndex){
        int xOffset = 50 + (keyIndex * 128);
        final int yOffset = 30;

        UISpriteBatch.draw(texture, xOffset, yOffset, 128, 128);
    }



    @Override
    public void dispose() {
        super.dispose();
        level.dispose();
    }
}
