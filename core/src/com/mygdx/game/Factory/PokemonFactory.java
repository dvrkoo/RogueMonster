package com.mygdx.game.Factory;

import com.mygdx.game.Characters.Bulbasaur;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Charmander;
import com.mygdx.game.Characters.Mew;
import com.mygdx.game.Characters.Mudkip;
import com.mygdx.game.Characters.Pikachu;
import com.mygdx.game.Utils.Enums.Pokemon;

public class PokemonFactory {
    Character p;

    public Character getPokemon(Pokemon pokemon, float x, float y){

        switch(pokemon){
            case BULBASAUR:{
                p = new Bulbasaur( x, y);
                return p;
            }
            case CHARMANDER:{
                p = new Charmander(x, y);
            return p;
            }
            case PIKACHU:{
                p = new Pikachu( x, y);
                return p;
            }
            case MUDKIP:{
                p = new Mudkip( x, y);
                return p;
            }
            case MEW:{
                p = new Mew( x, y);
                return p;
            }
        }
        return null;
    }
    
}
