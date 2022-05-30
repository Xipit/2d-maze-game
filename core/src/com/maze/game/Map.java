package com.maze.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    static TiledMap tiledMap = new TmxMapLoader().load("prototyp_tilemap.tmx");
    //TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);

    static float unitScale = 1;

    public static OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
}
