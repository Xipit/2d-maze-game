package com.maze.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.maze.game.Assets;
import com.maze.game.MazeGame;

import java.awt.*;

/**
 * <h1>Levelauswahl Screen</h1>
 * Erlaubt es das Level auszuwählen.
 *
 * @author  Hanno Witzleb, Lucas Neugebauer
 */
public class LevelSelectScreen extends ScreenAdapter {

    private final Point levelButtonDimensions = new Point(300, 150);

    private final Texture[] levelTextures = new Texture[Assets.LEVEL_DATA.length];
    private final Texture[] levelPressedTextures = new Texture[Assets.LEVEL_DATA.length];


    private final Point navigationButtonDimensions = new Point(100, 100);

    private final Texture backTexture;
    private final Texture forwardTexture;

    private final Texture escapeTexture;
    private final Point escapeButtonDimensions = new Point(50, 50);

    private final FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private final BitmapFont font;
    private String textColour = "#E68D33";
    private int textSize = 40;
    private int textIndentation = MazeGame.SCREEN_WIDTH / 20;

    private int startOfVisibleRange = 0;
    private int range = 3;
    private final int maxRange;

    private final SpriteBatch sb;


    public LevelSelectScreen(int startOfVisibleRange){
        this.maxRange = Assets.LEVEL_DATA.length ;
        this.startOfVisibleRange = Math.min(startOfVisibleRange, maxRange);
        Assets.loadLevelSelectMenuTextures();

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Assets.MENU_FONT));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = textSize;
        fontParameter.color = Color.valueOf(textColour);
        font = fontGenerator.generateFont(fontParameter);

        for (int i = 0; i < levelTextures.length; i++) {
            levelTextures[i] = Assets.manager.get(Assets.LEVEL_DATA[i].getButtonName());
            levelPressedTextures[i] = Assets.manager.get(Assets.LEVEL_DATA[i].getButtonPressedName());
        }

        backTexture = Assets.manager.get(Assets.LEVELS_BACKWARD);
        forwardTexture = Assets.manager.get(Assets.LEVELS_FORWARD);

        //TODO add special escape texture
        escapeTexture = Assets.manager.get(Assets.LEVELS_BACKWARD);

        sb = new SpriteBatch();
    }

    public LevelSelectScreen(){
        this(0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.input();

        sb.begin();

        font.draw(sb, "Movement using\nWASD\nor Arrow-Keys", textIndentation, textSize * 3 + textIndentation);

        for (int i = startOfVisibleRange; i < Math.min(startOfVisibleRange + range, Assets.LEVEL_DATA.length); i++) {
            drawLevelButton(levelTextures[i], levelPressedTextures[i], i);
        }
        drawNavigationButtons();
        drawEscapeButton(escapeTexture, escapeTexture);

        sb.end();
    }

    public void input(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            MazeGame.instance.setScreen(new MenuScreen());
        }
    }

    public void drawEscapeButton(Texture texture, Texture texturePressed){
        int xOffset = 50;
        final int yOffset = MazeGame.SCREEN_HEIGHT - 100;

        if (Gdx.input.getX() < xOffset + escapeButtonDimensions.x
                && Gdx.input.getX() > xOffset
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + escapeButtonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(texture, xOffset, yOffset, escapeButtonDimensions.x, escapeButtonDimensions.y);
            if(Gdx.input.justTouched()) {
                MazeGame.instance.setScreen(new MenuScreen());
            }
        }else {
            sb.draw(texturePressed, xOffset, yOffset, escapeButtonDimensions.x, escapeButtonDimensions.y);
        }

    }

    public void drawLevelButton(Texture texture, Texture texturePressed, int levelIndex){
        int xOffset = 100 + 300 * (levelIndex - startOfVisibleRange);
        final int yOffset = 450;

        if (Gdx.input.getX() < xOffset + levelButtonDimensions.x
                && Gdx.input.getX() > xOffset
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + levelButtonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(texture, xOffset, yOffset, levelButtonDimensions.x, levelButtonDimensions.y);
            if(Gdx.input.justTouched()) {
                Assets.loadTileMap(Assets.LEVEL_DATA[levelIndex].getFileName());
                MazeGame.instance.setScreen(new LevelScreen(Assets.LEVEL_DATA[levelIndex]));
            }
        }else {
            sb.draw(texturePressed, xOffset, yOffset, levelButtonDimensions.x, levelButtonDimensions.y);
        }

    }

    public void drawNavigationButtons(){
        int xOffsetBackward = 100 ;
        int xOffsetForward  = (100 + 300 * range) - navigationButtonDimensions.x;

        final int yOffset = 250;

        // Back button
        if (Gdx.input.getX() < xOffsetBackward + navigationButtonDimensions.x
                && Gdx.input.getX() > xOffsetBackward
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + navigationButtonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(backTexture, xOffsetBackward, yOffset, navigationButtonDimensions.x, navigationButtonDimensions.y);
            if(Gdx.input.justTouched()) {

                if(startOfVisibleRange == 0){
                    return;
                }
                this.startOfVisibleRange --;
            }
        }else {
            sb.draw(backTexture, xOffsetBackward, yOffset, navigationButtonDimensions.x, navigationButtonDimensions.y);
        }

        // Forward Button
        if (Gdx.input.getX() < xOffsetForward + navigationButtonDimensions.x
                && Gdx.input.getX() > xOffsetForward
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() < yOffset + navigationButtonDimensions.y
                && MazeGame.SCREEN_HEIGHT - Gdx.input.getY() > yOffset ) {

            sb.draw(forwardTexture, xOffsetForward, yOffset, navigationButtonDimensions.x, navigationButtonDimensions.y);
            if(Gdx.input.justTouched()) {

                if(startOfVisibleRange + range >= maxRange){
                    return;
                }
                this.startOfVisibleRange ++;

            }
        }else {
            sb.draw(forwardTexture, xOffsetForward, yOffset, navigationButtonDimensions.x, navigationButtonDimensions.y);
        }

    }
}
