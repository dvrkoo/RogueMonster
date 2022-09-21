package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Rectangle;

import com.mygdx.game.Maps.Island;
import com.mygdx.game.States.GameState;
import com.mygdx.game.States.StartingState;

import com.mygdx.game.Characters.Character;

public class Collision {

    public String getStarterpokemon(Rectangle pos) {
        String pkmn = "";
        if (pos.overlaps(StartingState.Mudkip)) {
            pkmn = "Mudkip";
        } else if (pos.overlaps(StartingState.Charmander)) {
            pkmn = "Charmender";
        } else if (pos.overlaps(StartingState.Bulbasaur)) {
            pkmn = "Bulbasaur";
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
