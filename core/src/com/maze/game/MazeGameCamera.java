package com.maze.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

/**
 * <h1>Kamera</h1>
 * Steuert die Position der Kamera und somit auch deren Verhalten wenn sich der Spieler bewegt.
 *
 * @author  Hanno Witzleb, Jörn Drechsler
 */
public class MazeGameCamera extends OrthographicCamera {

    public MazeGameCamera(float levelZoomFactor){
        super();

        this.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.zoom = levelZoomFactor;

        this.update();
    }

    public void update(Point playerCenter, int mapWidthPixel, int mapHeightPixel){
        // Center the player, unless the distance to the edge is not large enough to display only the map.
        Vector3 cameraPosition = this.position;

        Vector3 targetPosition = new Vector3(
                (Gdx.graphics.getWidth() >= (mapWidthPixel) * (1 / this.zoom))
                        ? (float) mapWidthPixel / 2
                        : (playerCenter.x < Gdx.graphics.getWidth() * this.zoom / 2)
                                ? (float) Gdx.graphics.getWidth() * this.zoom / 2
                                : Math.min(playerCenter.x, (mapWidthPixel - (float) Gdx.graphics.getWidth() * this.zoom / 2)),
                (Gdx.graphics.getHeight() >= (mapHeightPixel) * (1 / this.zoom))
                        ? (float)  mapHeightPixel / 2
                        : (playerCenter.y < Gdx.graphics.getHeight() * this.zoom / 2)
                                ? (float) Gdx.graphics.getHeight() * this.zoom / 2
                                : Math.min(playerCenter.y, (mapHeightPixel - (float) Gdx.graphics.getHeight() * this.zoom / 2)),
                0);


        // speed determines the delta between current camera position and target camera position
        // when target camera position does not move, the current camera position will catch up
        final float speed = 0.2F, isSpeed = 1.0F - speed;

        // scale all values of vectors
        cameraPosition.scl(isSpeed);
        targetPosition.scl(speed);

        cameraPosition.add(targetPosition);

        Vector3 flooredCameraPosition = new Vector3((float)Math.floor(cameraPosition.x), (float)Math.floor(cameraPosition.y), (float)Math.floor(cameraPosition.z));

        this.position.set(flooredCameraPosition);
        this.update();
    }
}
