package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.States.GameState;

public class Collision {
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
        for (Rectangle pkmn : GameState.pokemon) {
            if (pos.overlaps(pkmn) && pos != pkmn) {
                collision = true;
                // notifyObservers();
            }
        }
        return collision;
    }

    public boolean getPlayerCollision(Rectangle pos) {
        boolean collision = false;
        for (Rectangle pkmn : GameState.pokemon) {
            if (pos.overlaps(pkmn) && pos != pkmn) {
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
