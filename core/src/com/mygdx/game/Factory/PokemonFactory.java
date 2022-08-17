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

    public Character getPokemon(Pokemon pokemon){

        switch(pokemon){
            case BULBASAUR:{
                p = new Bulbasaur();
                return p;
            }
            case CHARMANDER:{
                p = new Charmander();
            return p;
            }
            case PIKACHU:{
                p = new Pikachu();
                return p;
            }
            case MUDKIP:{
                p = new Mudkip();
                return p;
            }
            case MEW:{
                p = new Mew();
                return p;
            }
        }
        return null;
    }
    
}
