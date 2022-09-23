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
        PSYCHIC, GHOST, STEEL, GROUND, ROCK, ICE, DRAGON, FAIRY, ELECTRIC, NULL;
    }

    public enum Pokemon {
        BULBASAUR, CHARMANDER, MEW, MUDKIP, PIKACHU, GRAVELER, STARAVIA, HUNTER, METANG, MARSHTOMP;

        private static final Random PRNG = new Random();
        private static final Pokemon[] state1 = {BULBASAUR, CHARMANDER, MEW, MUDKIP, PIKACHU};
        private static final Pokemon[] state2 = {GRAVELER, STARAVIA, HUNTER, METANG, MARSHTOMP};
        public static Pokemon randomPokemon1() {
            return state1[PRNG.nextInt(state1.length)];
        }
        public static Pokemon randomPokemon2() {
            return state2[PRNG.nextInt(state2.length)];
        }
    }

    public enum ItemType{
        POTION, SUPERPOTION, HYPERPOTION, REVIVE, ATTAKX, DEFX, SPEX;//NE HO MESSI UN PO, A CASO SE POI SERVIRANNO O NO LO VEDREMO
    }

}
