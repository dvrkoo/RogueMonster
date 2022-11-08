package com.mygdx.game.Factory;

import com.mygdx.game.Characters.Character;
import com.mygdx.game.Utils.Enums;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokemonFactoryTest {
PokemonFactory pkmfct = new PokemonFactory();
    @Test
    void getPokemon() {
        Character mudkip= pkmfct.getPokemon(Enums.Pokemon.MUDKIP);
        assertEquals(mudkip.getName(), "mudkip");
    }
}