package com.mygdx.game.Utils;

import java.util.Random;

public enum CharacterState{

    SOUTH,NORTH,EAST,WEST,STANDING;

    private static final Random PRNG = new Random(); 
    private static final CharacterState[] state = values();

    public static CharacterState randomDirection() { 
        return state[PRNG.nextInt(state.length)]; 
    }
}