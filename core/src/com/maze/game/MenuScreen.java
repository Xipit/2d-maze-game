package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


public class MenuScreen implements Screen {

    private static final int anknopf_breit = 300;
    private static final int anknopf_hoch = 150;
    private static final int ausknopf_breit = 300;
    private static final int ausknopf_hoch = 150;
    private static final int lvlknopf_breit = 300;
    private static final int lvlknopf_hoch = 150;

    MazeGame game;

    Texture gkan;
    Texture gkaus;
    Texture ekan;
    Texture ekaus;
    Texture lvan;
    Texture lvaus;
    Texture bg;

    public MenuScreen (MazeGame game){
        this.game = game;
        gkan = new Texture("anknopf.png");
        gkaus = new Texture("anknopf_an.png");
        ekan = new Texture("endeknopf.png");
        ekaus = new Texture("endeknopf_an.png");
        lvan = new Texture("Level_an.png");
        lvaus = new Texture("Level_aus.png");
        bg = new Texture("background.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        int x = MazeGame.WIDTH / 2 - ausknopf_breit / 2;
        if (Gdx.input.getX() < x + ausknopf_breit-100 && Gdx.input.getX() > x && MazeGame.HEIGHT - Gdx.input.getY() < 50 + ausknopf_hoch && MazeGame.HEIGHT - Gdx.input.getY() > 50 ) {
            game.batch.draw(ekaus, x + ausknopf_breit, 100, ausknopf_breit, ausknopf_hoch);
                if(Gdx.input.justTouched()) {
                    Gdx.app.exit();
            }
        }else {
            game.batch.draw(ekan, x + ausknopf_breit, 100, ausknopf_breit, ausknopf_hoch);
        }

        if (Gdx.input.getX() < x + anknopf_breit-100 && Gdx.input.getX() > x && MazeGame.HEIGHT - Gdx.input.getY() < 350 + anknopf_hoch && MazeGame.HEIGHT - Gdx.input.getY() > 350 ) {
            game.batch.draw(gkaus, x + anknopf_breit, 550, anknopf_breit, anknopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));

            }

            }else {
            game.batch.draw(gkan, x + anknopf_breit, 550, anknopf_breit, anknopf_hoch);
        }

        if (Gdx.input.getX() < x + lvlknopf_breit-100 && Gdx.input.getX() > x && MazeGame.HEIGHT - Gdx.input.getY() < 200 + lvlknopf_hoch && MazeGame.HEIGHT - Gdx.input.getY() > 200 ) {
            game.batch.draw(lvan, x + lvlknopf_breit, 350, lvlknopf_breit, lvlknopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new AuswahlScreen(game));

            }

        }else {
            game.batch.draw(lvaus, x + lvlknopf_breit, 350, lvlknopf_breit, lvlknopf_hoch);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
