package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MazeGame extends ApplicationAdapter {
    private Texture dropImage;
    private Texture bucketImage;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Rectangle bucket;

    @Override
    public void create(){
        // load the images
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        // actual objects (origin of x,y is the bottom left of the screen)
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0,0, 0.2f, 1);

        camera.update();

        // render
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        batch.end();


        // input
        if(Gdx.input.isTouched()){
            // touch movement
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // transform touchPos to camera coordinate system
            bucket.x = touchPos.x - 64 / 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure bucket stays within screen limits
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;
    }
}
