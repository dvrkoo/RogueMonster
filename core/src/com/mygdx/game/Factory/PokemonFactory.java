package com.mygdx.game.Factory;

import com.mygdx.game.Characters.Bulbasaur;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Charmander;
import com.mygdx.game.Characters.Graveler;
import com.mygdx.game.Characters.Hunter;
import com.mygdx.game.Characters.Marshtomp;
import com.mygdx.game.Characters.Metang;
import com.mygdx.game.Characters.Mew;
import com.mygdx.game.Characters.Mudkip;
import com.mygdx.game.Characters.Pikachu;
import com.mygdx.game.Characters.Staravia;
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
            case MARSHTOMP:{
                p = new Marshtomp();
                return p;
            }
            case METANG:{
                p = new Metang();
                return p;
            }
            case STARAVIA:{
                p = new Staravia();
                return p;
            }
            case GRAVELER:{
                p = new Graveler();
                return p;
            }
            case HUNTER:{
                p = new Hunter();
                return p;
            }
        }
        return null;
    }
    
}
