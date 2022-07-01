package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.Assets;
import com.maze.game.MazeGame;
import com.maze.game.levels.Level;

public class LevelSelectScreen extends ScreenAdapter {

    private final int buttonWidth = 300;
    private final int buttonHeight = 150;

    private final Texture[] levelTextures = new Texture[Assets.LEVEL_BUTTONS.length];
    private final Texture[] levelPressedTextures = new Texture[Assets.LEVEL_BUTTONS_PRESSED.length];


    private final int navigationButtonWidth = 100;
    private final int navigationButtonHeight = 100;
    private final Texture backTexture;
    private final Texture forwardTexture;


    private int startOfVisibleRange = 0;
    private int range = 3;
    private final int maxRange;

    private final Texture backgroundTexture;

    private final SpriteBatch sb;

    public LevelSelectScreen(){
        maxRange = Assets.LEVEL_BUTTONS.length - 1;
        Assets.loadLevelSelectMenuTextures();

        for (int i = 0; i < levelTextures.length; i++) {
            levelTextures[i] = Assets.manager.get(Assets.LEVEL_BUTTONS[i]);
            levelPressedTextures[i] = Assets.manager.get(Assets.LEVEL_BUTTONS_PRESSED[i]);
        }

        backTexture = Assets.manager.get(Assets.LEVEL_BUTTONS[0]);
        forwardTexture = Assets.manager.get(Assets.LEVEL_BUTTONS[0]);

        backgroundTexture = Assets.manager.get(Assets.LEVELS_BACKGRUND);

        sb = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sb.begin();

        sb.draw(backgroundTexture, 0, 0);

        for (int i = startOfVisibleRange; i < startOfVisibleRange + range; i++) {
            try {
                drawLevelButton(levelTextures[i], levelPressedTextures[i], i);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        drawNavigationButtons();


        sb.end();


    }

    public void drawLevelButton(Texture texture, Texture texturePressed, int range) throws InstantiationException, IllegalAccessException {
        int xOffset = 100 + 300 * range;
        final int y = 450;

        if (Gdx.input.getX() < xOffset + buttonWidth
                && Gdx.input.getX() > xOffset
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + buttonHeight
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(texture, xOffset, y, buttonWidth, buttonHeight);
            if(Gdx.input.justTouched()) {
                Assets.loadTileMap((Level) Assets.LEVELS[range].);
                MazeGame.instance.setScreen((Screen) Assets.LEVELS[range].newInstance());
            }
        }else {
            sb.draw(texturePressed, xOffset, y, buttonWidth, buttonHeight);
        }

    }

    public void drawNavigationButtons(){
        int xOffsetBackward = 100 ;
        int xOffsetForward  = (100 + 300 * range) - navigationButtonWidth;

        final int y = 250;

        if (Gdx.input.getX() < xOffsetBackward + navigationButtonWidth
                && Gdx.input.getX() > xOffsetBackward
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + navigationButtonHeight
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(backTexture, xOffsetBackward, y, navigationButtonWidth, navigationButtonHeight);
            if(Gdx.input.justTouched()) {

                if(range == maxRange){
                    return;
                }
                this.startOfVisibleRange ++;
            }
        }else {
            sb.draw(backTexture, xOffsetBackward, y, navigationButtonWidth, navigationButtonHeight);
        }

        if (Gdx.input.getX() < xOffsetBackward + navigationButtonWidth
                && Gdx.input.getX() > xOffsetBackward
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < y + navigationButtonHeight
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > y ) {

            sb.draw(forwardTexture, xOffsetForward, y, navigationButtonWidth, navigationButtonHeight);
            if(Gdx.input.justTouched()) {

                if(range == 0){
                    return;
                }
                this.startOfVisibleRange --;
            }
        }else {
            sb.draw(forwardTexture, xOffsetForward, y, navigationButtonWidth, navigationButtonHeight);
        }

    }
}
