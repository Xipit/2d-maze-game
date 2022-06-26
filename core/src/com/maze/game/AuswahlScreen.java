package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AuswahlScreen implements Screen {

    private static final int lv1knopf_breit = 300;
    private static final int lv1knopf_hoch = 150;
    private static final int lv2knopf_breit = 300;
    private static final int lv2knopf_hoch = 150;
    private static final int lv3knopf_breit = 300;
    private static final int lv3knopf_hoch = 150;

    MazeGame game;
    SpriteBatch batch;

    Texture lv1an;
    Texture lv1aus;
    Texture lv2an;
    Texture lv2aus;
    Texture lv3an;
    Texture lv3aus;

    public AuswahlScreen(MazeGame game){
        this.game = game;
        batch = new SpriteBatch();
        lv1an = new Texture("lv1_an.png");
        lv1aus = new Texture("lv1_aus.png");
        lv2an = new Texture("lv2_an.png");
        lv2aus = new Texture("lv2_aus.png");
        lv3an = new Texture("lv3_an.png");
        lv3aus = new Texture("lv3_aus.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        int x = MazeGame.WIDTH / 2 - lv1knopf_breit / 2;
        if (Gdx.input.getX() < x + lv3knopf_breit-100 && Gdx.input.getX() > x && MazeGame.HEIGHT - Gdx.input.getY() < 50 + lv3knopf_hoch && MazeGame.HEIGHT - Gdx.input.getY() > 50 ) {
            game.batch.draw(lv3an, x + lv3knopf_breit, 100, lv3knopf_breit, lv1knopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }else {
            game.batch.draw(lv3aus, x + lv1knopf_breit, 100, lv1knopf_breit, lv1knopf_hoch);
        }

        if (Gdx.input.getX() < x + lv1knopf_breit-100 && Gdx.input.getX() > x && MazeGame.HEIGHT - Gdx.input.getY() < 350 + lv1knopf_hoch && MazeGame.HEIGHT - Gdx.input.getY() > 350 ) {
            game.batch.draw(lv1an, x + lv1knopf_breit, 550, lv1knopf_breit, lv1knopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            }

        }else {
            game.batch.draw(lv1aus, x + lv1knopf_breit, 550, lv1knopf_breit, lv1knopf_hoch);
        }

        if (Gdx.input.getX() < x + lv2knopf_breit-100 && Gdx.input.getX() > x && MazeGame.HEIGHT - Gdx.input.getY() < 200 + lv2knopf_hoch && MazeGame.HEIGHT - Gdx.input.getY() > 200 ) {
            game.batch.draw(lv2an, x + lv2knopf_breit, 350, lv2knopf_breit, lv2knopf_hoch);
            if(Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            }

        }else {
            game.batch.draw(lv2aus, x + lv2knopf_breit, 350, lv2knopf_breit, lv2knopf_hoch);
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
