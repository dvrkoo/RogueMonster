package com.mygdx.game.Utils;

import java.util.Random;

public class Enums {

    public enum TILETYPE {
        GRASS,
        WATER,
        CLIFF,
        TREE
    }

    public enum CharacterState {

        SOUTH, NORTH, EAST, WEST, STANDING;

        private static final Random PRNG = new Random();
        private static final CharacterState[] state = values();

        public static CharacterState randomDirection() {
            return state[PRNG.nextInt(state.length)];

            // return state[PRNG.nextInt(state.length)];
        }
    }

    public enum PokemonType {
        WATER, GRASS, FIRE, BUG, POISON, NORMAL, FLYING, FIGHT, DARK,
        PSYCHIC, GHOST, STEEL, GROUND, ROCK, ICE, DRAGON, FAIRY, ELECTRIC
    }

    public enum Pokemon {
        BULBASAUR, CHARMANDER, MEW, MUDKIP, PIKACHU;

        private static final Random PRNG = new Random();
        private static final Pokemon[] state = values();

        public static Pokemon randomPokemon() {
            return state[PRNG.nextInt(state.length)];
        }
    }

}
