package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.States.GameState;
import com.mygdx.game.States.StartingState;

public class Collision {

    private final String BLOCKEDKEY = "Collisions";

    TmxMapLoader loader = new TmxMapLoader();
    public TiledMap map = loader.load("Maps/lab2.tmx");

    TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Collisions");
    MapObjects objects = collisionObjectLayer.getObjects();

    // there are several other types, Rectangle is probably the most common one
    public void getMapCollisions(Rectangle pos) {

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            System.out.print("test");
            if (pos.overlaps(rectangle)) {
                System.out.print("true");
            }
        }
    }

    public boolean getCollision(Rectangle pos) {
        boolean collision = false;
        for (Rectangle element : Island.collisionRectangle) {
            if (pos.overlaps(element)) {
                collision = true;
                // notifyObservers();
            }
        }

        return collision;
    }

    public boolean getPkmnCollision(Rectangle pos) {
        boolean collision = false;
        if (GameState.pokemon != null) {
            for (Rectangle pkmn : GameState.pokemon) {
                if (pos.overlaps(pkmn) && pos != pkmn) {
                    collision = true;
                    // notifyObservers();
                }
            }
        }
        return collision;
    }

    public boolean getPlayerCollision(Rectangle pos) {
        boolean collision = false;
        for (Rectangle pkmn : GameState.pokemon) {
            if (pos.overlaps(pkmn)) {
                collision = true;
                // notifyObservers();
            }
        }
        return collision;
    }

    /*
     * @Override
     * public void update(Object o) {
     * //System.out.println(collision);
     * }
     */

}
