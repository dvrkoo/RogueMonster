package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.States.GameState;
import com.mygdx.game.States.StartingState;
import com.mygdx.game.Characters.Character;

public class Collision {

    // there are several other types, Rectangle is probably the most common one
    public boolean getMapCollisions(Rectangle pos) {
        boolean collision = false;
        for (Rectangle rec : StartingState.rectangleArray) {
            if (pos.overlaps(rec)) {
                collision = true;
            }
        }
        return collision;
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
        for (Character pkmn : GameState.pokemon) {
            if (pos.overlaps(pkmn)) {
                collision = true;
                pkmn.isCollided = true;
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
