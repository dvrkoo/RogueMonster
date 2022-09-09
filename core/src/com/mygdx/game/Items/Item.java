package com.mygdx.game.Items;

import com.mygdx.game.Characters.Character;
import com.mygdx.game.Utils.Enums.ItemType;

public class Item {

    ItemType type;
    String itemName;
    
    public Item(ItemType type){
        this.type = type;   
        itemInit();     
    }

    void itemInit(){
        switch(this.type){
            case POTION:{
                itemName = "Potion";
                break;
            }
            case SUPERPOTION:{
                itemName = "Superpotion";
                break;
            }
            case HYPERPOTION:{
                itemName = "Hyperpotion";
                break;
            }
            case ATTAKX:{
                itemName = "Attack X";
                break;
            }
            case DEFX:{
                itemName = "Defence X";
                break;
            }
            case SPEX:{
                itemName = "Speed X";
                break;
            }
            case REVIVE:{
                itemName = "Revive";
                break;
            }
        }
    }

    

    public void useItem(Character pokemon){
        switch(this.type){
            case POTION:{
                pokemon.setHp(pokemon.getHp() + 20);
                break;
            }
            case SUPERPOTION:{
                pokemon.setHp(pokemon.getHp() + 50);
                break;
            }
            case HYPERPOTION:{
                pokemon.setHp(pokemon.getHp() + 100);
                break;
            }
            case ATTAKX:{
                //aumenta attacco in battaglia
            }
            case DEFX:{
                //aumenta difesa in battaglia
            }
            case SPEX:{
                //aumenta velocità in battaglia
            }
            case REVIVE:{
                //se un pkm muore lo fa tornare in vita subito
            }
        }
    }
    public String getItemName() {
        return itemName;
    }

}
