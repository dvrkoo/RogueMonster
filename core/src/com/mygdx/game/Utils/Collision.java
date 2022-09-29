package com.mygdx.game.Utils;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.game.Maps.Island;
import com.mygdx.game.States.GameState;
import com.mygdx.game.States.StartingState;
import com.mygdx.game.Characters.Character;

public class Collision {

    public int getStarterpokemon(Rectangle pos) {
        int pkmn = 0;
        if (pos.overlaps(StartingState.starterCollision)) {
            pkmn = 1;
        } else if (pos.overlaps(StartingState.starterCollision2)) {
            pkmn = 2;
        } else if (pos.overlaps(StartingState.starterCollision3)) {
            pkmn = 3;
        }
        return pkmn;
    }

    public boolean getMapCollisions(Rectangle pos) {
        boolean collision = false;
        for (Rectangle rec : StartingState.rectangleArray) {
            if (pos.overlaps(rec)) {
                collision = true;
            }
        }
        return collision;
    }

    public Rectangle getDialogueCollisions(TiledMapTileLayer collisionObjectLayer) {

        Rectangle rectangle = new Rectangle();
        for (int x = 0; x < collisionObjectLayer.getWidth(); x++) {
            for (int y = 0; y < collisionObjectLayer.getHeight(); y++) {
                Cell cell = collisionObjectLayer.getCell(x, y);
                if (cell != null) {
                    rectangle.x = x * 32;
                    rectangle.y = y * 32;
                }
            }
        }
        return rectangle;
    }

    public ArrayList<Rectangle> getStartingCollisionArray(TiledMapTileLayer collisionObjectLayer) {
        ArrayList<Rectangle> rectangleArray = new ArrayList<Rectangle>();
        for (int x = 0; x < collisionObjectLayer.getWidth(); x++) {
            for (int y = 0; y < collisionObjectLayer.getHeight(); y++) {
                Cell cell = collisionObjectLayer.getCell(x, y);
                if (cell != null) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.x = x * 32;
                    rectangle.y = y * 32;
                    rectangleArray.add(rectangle);
                }
            }
        }
        return rectangleArray;
    }

    public boolean getCollision(Rectangle pos) {
        boolean collision = false;
        for (Rectangle element : Island.collisionRectangle) {
            if (pos.overlaps(element)) {
                collision = true;
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
                }
            }
        }
        return collision;
    }

    public boolean getPlayerCollision(Rectangle pos) {
        boolean collision = false;
        for (Character pkmn : GameState.pokemon) {
            if (pos.overlaps(pkmn)) {
                collision = true;
                pkmn.isOpponent = true;
            }

        }
        return collision;
    }

}
