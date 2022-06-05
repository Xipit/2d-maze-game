package com.maze.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);

    public Map create(String fileName, Float unitScale){
        this.map = MazeGame.instance.assetManager.get(fileName, TiledMap.class);
        renderer = new OrthogonalTiledMapRenderer(this.map, unitScale);
        return this;
    }

    public void render(OrthographicCamera camera) {
        renderer.setMap(this.map);
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        this.map.dispose();
    }
}
