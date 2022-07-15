package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.Assets;
import com.maze.game.MazeGame;
import com.maze.game.levels.LevelData;

import java.awt.*;

/**
 * <h1>VictoryScreen</h1>
 * Erscheint nach dem ein Level abgeschlossen wurde
 *
 * @author  Hanno Witzleb, Jörn Drechsler, Lucas Neugebauer
 */
public class VictoryScreen extends ScreenAdapter {
    private final Texture victoryImageTexture;

    private final Point nextLevelButtonDimension = new Point(300, 150);
    private final Texture nextLevelTexture;
    private final Texture nextLevelTexturePressed;

    private final Point repeatLevelButtonDimension = new Point(150, 150);
    private final Texture repeatLevelTexture;
    private final Texture repeatLevelTexturePressed;

    private final Point backButtonDimensions = new Point(100, 100);
    private final Texture backTexture;
    private final Texture backTexturePressed;

    private SpriteBatch sb;
    private Point resolutionSpriteBatch = new Point(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    private final LevelData wonLevelData;

    private boolean isDisposed = false;

    public VictoryScreen(LevelData levelData) {
        this.wonLevelData = levelData;

        Assets.loadVictoryMenuTextures();

        victoryImageTexture = Assets.manager.get(Assets.VICTORY_IMAGE);
        nextLevelTexture = Assets.manager.get(Assets.NEXT_LEVEL);
        nextLevelTexturePressed = Assets.manager.get(Assets.NEXT_LEVEL_PRESSED);
        repeatLevelTexture = Assets.manager.get(Assets.RELOAD_LEVEL);
        repeatLevelTexturePressed = Assets.manager.get(Assets.RELOAD_LEVEL_PRESSED);
        backTexture = Assets.manager.get(Assets.LEVELS_BACKWARD);
        backTexturePressed = Assets.manager.get(Assets.LEVELS_BACKWARD_PRESSED);

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if(isDisposed) return;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Gdx.graphics.getWidth() returns the changed window size correctly first after rendering several times
        if (Gdx.graphics.getWidth() != resolutionSpriteBatch.x || Gdx.graphics.getHeight() != resolutionSpriteBatch.y) {
            sb = new SpriteBatch();
            resolutionSpriteBatch = new Point(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        this.input();

        sb.begin();
        sb.draw(victoryImageTexture, 200, 200);

        drawBackButton(backTexture, backTexturePressed);
        drawNextLevelButton(nextLevelTexture, nextLevelTexturePressed);
        drawRepeatLevelButton(repeatLevelTexture, repeatLevelTexturePressed);

        sb.end();
    }

    public void input(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            MazeGame.instance.setScreen(new LevelSelectScreen());
        }
    }

    public void drawBackButton(Texture texture, Texture texturePressed){
        int xOffset = 200;
        final int yOffset = 20;

        if (Gdx.input.getX() < xOffset + backButtonDimensions.x
                && Gdx.input.getX() > xOffset
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + backButtonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(texturePressed, xOffset, yOffset, backButtonDimensions.x, backButtonDimensions.y);
            if(Gdx.input.justTouched()) {
                this.dispose();
                MazeGame.instance.setScreen(new LevelSelectScreen(wonLevelData.findIndex()));
            }
        }else {
            sb.draw(texture, xOffset, yOffset, backButtonDimensions.x, backButtonDimensions.y);
        }

    }

    public void drawNextLevelButton(Texture texture, Texture texturePressed){
        int xOffset = 582;
        final int yOffset = 20;

        if (Gdx.input.getX() < xOffset + nextLevelButtonDimension.x
                && Gdx.input.getX() > xOffset
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + nextLevelButtonDimension.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(texturePressed, xOffset, yOffset, nextLevelButtonDimension.x, nextLevelButtonDimension.y);
            if(Gdx.input.justTouched()) {
                this.dispose();
                MazeGame.instance.setScreen(new LevelScreen(Assets.LEVEL_DATA[Math.min(wonLevelData.findIndex() + 1, Assets.LEVEL_DATA.length - 1)]));
            }
        }else {
            sb.draw(texture, xOffset, yOffset, nextLevelButtonDimension.x, nextLevelButtonDimension.y);
        }
    }

    public void drawRepeatLevelButton(Texture texture, Texture texturePressed){
        int xOffset = 422;
        final int yOffset = 20;

        if (Gdx.input.getX() < xOffset + repeatLevelButtonDimension.x
                && Gdx.input.getX() > xOffset
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + repeatLevelButtonDimension.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(texturePressed, xOffset, yOffset, repeatLevelButtonDimension.x, repeatLevelButtonDimension.y);
            if(Gdx.input.justTouched()) {
                this.dispose();
                MazeGame.instance.setScreen(new LevelScreen(Assets.LEVEL_DATA[wonLevelData.findIndex()]));
            }
        }else {
            sb.draw(texture, xOffset, yOffset, repeatLevelButtonDimension.x, repeatLevelButtonDimension.y);
        }

    }

    @Override
    public void dispose() {
        this.isDisposed = true;
        super.dispose();
    }
}
