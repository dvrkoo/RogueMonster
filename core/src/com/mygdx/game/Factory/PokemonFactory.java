package com.mygdx.game.Factory;

import com.mygdx.game.Characters.Bulbasaur;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Charmander;
import com.mygdx.game.Characters.Mew;
import com.mygdx.game.Characters.Mudkip;
import com.mygdx.game.Characters.Pikachu;

public class PokemonFactory {
    Character p;

    public Character getPokemon(String pokemon, int posX, int posY){
        if(pokemon == null)
            return null;

        if(pokemon.equalsIgnoreCase("Bulbasaur")){
            p = new Bulbasaur( posX, posY);
            return p;
        } else if(pokemon.equalsIgnoreCase("Mudkip")){
            Character p = new Mudkip(posX, posY);
            return p;
        }else if(pokemon.equalsIgnoreCase("Pikachu")){
            Character p= new Pikachu(posX, posY);
            return p;
        }else if(pokemon.equalsIgnoreCase("Charmander")){
            Character p = new Charmander(posX, posY);
            return p;
        }else if(pokemon.equalsIgnoreCase("Mew")){
            Character p = new Mew(posX, posY);
            return p;
        }        
        return null;
    }
    
}
