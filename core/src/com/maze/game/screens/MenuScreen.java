package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.Assets;
import com.maze.game.MazeGame;

import java.awt.*;

/**
 * <h1>Startmenü Screen</h1>
 * Führt zur Levelauswahl. Hat Button um das Spiel zu beenden.
 *
 * @author  Hanno Witzleb, Lucas Neugebauer
 */
public class MenuScreen extends ScreenAdapter {

    private static final Point buttonDimensions = new Point(300, 150);

    private final SpriteBatch sb;

    Texture endTexture;
    Texture endTexturePressed;
    Texture levelTexture;
    Texture levelTexturePressed;
    Texture backgroundTexture;

    public MenuScreen (){
        Assets.loadMenuTextures();

        endTexture =            Assets.manager.get(Assets.END_BUTTON);
        endTexturePressed =     Assets.manager.get(Assets.END_BUTTON_PRESSED);
        levelTexture =          Assets.manager.get(Assets.LEVEL_BUTTON);
        levelTexturePressed =   Assets.manager.get(Assets.LEVEL_BUTTON_PRESSED);
        backgroundTexture =     Assets.manager.get(Assets.BACKGROUND);

        sb = new SpriteBatch();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.begin();

        sb.draw(backgroundTexture, 0, 0);

        drawLevelButton();
        drawEndButton();

        sb.end();
    }


    public void drawLevelButton(){
        final int middle = MazeGame.SCREEN_WIDTH / 2 - buttonDimensions.x / 2;
        final int y = 200;

        if (Gdx.input.getX() < middle + buttonDimensions.x
                && Gdx.input.getX() > middle
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + buttonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(levelTexture, middle, y, buttonDimensions.x, buttonDimensions.x);
            if(Gdx.input.justTouched()) {
                this.dispose();
                MazeGame.instance.setScreen(new LevelSelectScreen());

            }

        }else {
            sb.draw(levelTexturePressed, middle, y, buttonDimensions.x, buttonDimensions.y);
        }
    }

    public void drawEndButton(){
        final int middle = MazeGame.SCREEN_WIDTH / 2 - buttonDimensions.x / 2;
        final int y = 50;

        if (Gdx.input.getX() < middle + buttonDimensions.x
                && Gdx.input.getX() > middle
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + buttonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(endTexturePressed, middle, y, buttonDimensions.x, buttonDimensions.y);
            if(Gdx.input.justTouched()) {
                Gdx.app.exit();
            }
        }else {
            sb.draw(endTexture, middle, y, buttonDimensions.x, buttonDimensions.y);
        }
    }
}