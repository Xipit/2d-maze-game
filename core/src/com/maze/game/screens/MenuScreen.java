package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.maze.game.Assets;
import com.maze.game.MazeGame;

import java.awt.*;


public class MenuScreen extends ScreenAdapter {

    private static final int anknopf_breit = 300;
    private static final int anknopf_hoch = 150;
    private static final int ausknopf_breit = 300;
    private static final int ausknopf_hoch = 150;
    private static final int lvlknopf_breit = 300;
    private static final int lvlknopf_hoch = 150;

    private final SpriteBatch sb;

    Texture gkan;
    Texture gkaus;
    Texture ekan;
    Texture ekaus;
    Texture lvan;
    Texture lvaus;
    Texture bg;

    public MenuScreen (){


        Assets.loadMenuTextures();
        //TODO migrate pictures to Assets
        gkan =   Assets.manager.get(Assets.GAME_BUTTON);
        gkaus = Assets.manager.get(Assets.GAME_BUTTON_PRESSED);
        ekan =  Assets.manager.get(Assets.END_BUTTON);
        ekaus = Assets.manager.get(Assets.END_BUTTON_PRESSED);
        lvan =  Assets.manager.get(Assets.LEVEL_BUTTON);
        lvaus = Assets.manager.get(Assets.LEVEL_BUTTON_PRESSED);
        bg =    Assets.manager.get(Assets.BACKGROUND);

        sb = new SpriteBatch();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.begin();

        sb.draw(bg, 0, 0);

        drawLevelButton();
        drawEndButton();

        sb.end();

    }

    public void drawLevelButton(){
        final int middle = MazeGame.SCREEN_WIDTH / 2 - ausknopf_breit / 2;
        final int y = 200;

        if (Gdx.input.getX() < middle + lvlknopf_breit
                && Gdx.input.getX() > middle
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + lvlknopf_hoch
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(lvan, middle, y, lvlknopf_breit, lvlknopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                MazeGame.instance.setScreen(new LevelSelectScreen());

            }

        }else {
            sb.draw(lvaus, middle, y, lvlknopf_breit, lvlknopf_hoch);
        }
    }

    public void drawEndButton(){
        final int middle = MazeGame.SCREEN_WIDTH / 2 - ausknopf_breit / 2;
        final int y = 50;

        if (Gdx.input.getX() < middle + ausknopf_breit
                && Gdx.input.getX() > middle
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + ausknopf_hoch
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(ekaus, middle, y, ausknopf_breit, ausknopf_hoch);
            if(Gdx.input.justTouched()) {
                Gdx.app.exit();
            }
        }else {
            sb.draw(ekan, middle, y, ausknopf_breit, ausknopf_hoch);
        }
    }

    public  void drawGameButton(){
        int middle = MazeGame.SCREEN_WIDTH / 2 - ausknopf_breit / 2;
        if (Gdx.input.getX() < middle + anknopf_breit
                && Gdx.input.getX() > middle
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < 550 + anknopf_hoch
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > 550 ) {

            sb.draw(gkaus, middle, 550, anknopf_breit, anknopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                MazeGame.instance.setScreen(new LevelScreen());

            }

        }else {
            sb.draw(gkan, middle, 550, anknopf_breit, anknopf_hoch);
        }
    }
}