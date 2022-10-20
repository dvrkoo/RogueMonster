package com.mygdx.game.Utils;

import com.mygdx.game.Characters.Character;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Damage {

    float getModifier( PokemonType type, PokemonType attackType ){

        float modifier = 1;
        switch(type){
            case WATER:{
                if( attackType == PokemonType.ELECTRIC || attackType == PokemonType.GRASS )
                    modifier = 2;
                else if( attackType == PokemonType.ICE || attackType == PokemonType.STEEL || attackType == PokemonType.WATER || attackType == PokemonType.FIRE )
                    modifier = 0.5f;
                break;
            }
            case NORMAL:{
                if( attackType == PokemonType.FIGHT )
                    modifier = 2;
                else if( attackType == PokemonType.GHOST )
                    modifier = 0;
                break;
            }
            case FIRE:{
                if( attackType == PokemonType.GROUND || attackType == PokemonType.ROCK || attackType == PokemonType.WATER )
                    modifier = 2;
                else if( attackType == PokemonType.BUG || attackType == PokemonType.STEEL || attackType == PokemonType.FIRE || attackType == PokemonType.GRASS || attackType == PokemonType.ICE || attackType == PokemonType.FAIRY )
                    modifier = 0.5f;
                break;
            }
            case FIGHT:{
                if( attackType == PokemonType.FLYING || attackType == PokemonType.PSYCHIC || attackType == PokemonType.FAIRY )
                    modifier = 2;
                else if( attackType == PokemonType.ROCK || attackType == PokemonType.BUG || attackType == PokemonType.DARK )
                    modifier = 0.5f;
                break;
            }
            case FLYING:{
                if( attackType == PokemonType.ROCK || attackType == PokemonType.ELECTRIC || attackType == PokemonType.ICE )
                    modifier = 2;
                else if( attackType == PokemonType.FIGHT || attackType == PokemonType.BUG || attackType == PokemonType.GRASS)
                    modifier = 0.5f;
                else if ( attackType == PokemonType.GROUND)
                    modifier = 0;
                break;
            }
            case GRASS:{
                if ( attackType == PokemonType.FLYING || attackType == PokemonType.POISON || attackType == PokemonType.BUG || attackType == PokemonType.FIRE || attackType == PokemonType.ICE )
                    modifier = 2;
                else if( attackType == PokemonType.GROUND || attackType == PokemonType.WATER || attackType == PokemonType.GRASS || attackType == PokemonType.ELECTRIC )
                    modifier = 0.5f;
                break;
            }
            case POISON:{
                if( attackType == PokemonType.GROUND || attackType == PokemonType.PSYCHIC)
                    modifier = 2;
                else if( attackType == PokemonType.FIGHT || attackType == PokemonType.POISON || attackType == PokemonType.BUG || attackType == PokemonType.GRASS || attackType == PokemonType.FAIRY)
                    modifier = 0.5f;
                break;
            }
            case ELECTRIC:{
                if( attackType == PokemonType.GROUND)
                    modifier = 2;
                else if( attackType == PokemonType.FLYING || attackType == PokemonType.STEEL || attackType == PokemonType.ELECTRIC)
                    modifier = 0.5f;
                break;
            }
            case GROUND:{
                if ( attackType == PokemonType.WATER || attackType == PokemonType.GRASS || attackType == PokemonType.ICE )
                    modifier = 2;
                else if( attackType == PokemonType.POISON || attackType == PokemonType.ROCK )
                    modifier = 0.5f;
                else if( attackType == PokemonType.ELECTRIC)
                    modifier = 0;
                break;
            }
            case PSYCHIC:{
                if( attackType == PokemonType.BUG || attackType == PokemonType.GHOST || attackType == PokemonType.DARK)
                    modifier = 2;
                else if( attackType == PokemonType.FIGHT || attackType == PokemonType.PSYCHIC)
                    modifier = 0.5f;
                break;
            }
            case ROCK:{
                if( attackType == PokemonType.FIGHT || attackType == PokemonType.GROUND || attackType == PokemonType.STEEL || attackType == PokemonType.WATER || attackType == PokemonType.GRASS)
                    modifier = 2;
                else if( attackType == PokemonType.NORMAL || attackType == PokemonType.FLYING || attackType == PokemonType.POISON || attackType == PokemonType.FIRE)
                    modifier = 0.5f;
                break;
            }
            case ICE:{
                if ( attackType == PokemonType.FIGHT || attackType == PokemonType.ROCK || attackType == PokemonType.STEEL || attackType == PokemonType.FIRE)
                    modifier = 2;
                else if( attackType == PokemonType.ICE)
                    modifier = 0.5f;
                break;
            }
            case BUG:{
                if( attackType == PokemonType.FLYING || attackType == PokemonType.ROCK || attackType == PokemonType.FIRE)
                    modifier = 2;
                else if( attackType == PokemonType.FIGHT || attackType == PokemonType.GROUND || attackType == PokemonType.GRASS )
                    modifier = 0.5f;
                break;
            }
            case DRAGON:{
                if( attackType == PokemonType.ICE || attackType == PokemonType.DRAGON || attackType == PokemonType.FAIRY)
                    modifier = 2;
                else if( attackType == PokemonType.WATER || attackType == PokemonType.FIRE || attackType == PokemonType.GRASS || attackType == PokemonType.ELECTRIC)
                    modifier = 0.5f;
                break;
            }
            case GHOST:{
                if( attackType == PokemonType.GHOST || attackType == PokemonType.DARK)
                    modifier = 2;
                else if( attackType == PokemonType.POISON || attackType == PokemonType.BUG )
                    modifier = 0.5f;
                else if( attackType == PokemonType.NORMAL || attackType == PokemonType.FIGHT )
                    modifier = 0;
                break;
            }
            case DARK:{
                if( attackType == PokemonType.FIGHT || attackType == PokemonType.BUG || attackType == PokemonType.FAIRY)
                    modifier = 2;
                else if( attackType == PokemonType.GHOST || attackType == PokemonType.DARK )
                    modifier = 0.5f;
                else if( attackType == PokemonType.PSYCHIC)
                    modifier = 0;
                break;
            }
            case STEEL:{
                if( attackType == PokemonType.FIGHT || attackType == PokemonType. GROUND || attackType == PokemonType.FIRE)
                    modifier = 2;
                else if( attackType == PokemonType.NORMAL || attackType == PokemonType.FLYING || attackType == PokemonType.ROCK || attackType == PokemonType.BUG || attackType == PokemonType.STEEL || attackType == PokemonType.GRASS || attackType == PokemonType.PSYCHIC || attackType == PokemonType.ICE || attackType == PokemonType.DRAGON || attackType == PokemonType.FAIRY )
                    modifier = 0.5f;
                else if ( attackType == PokemonType.POISON )
                    modifier = 0.5f;
                break;
            }
            case FAIRY:{
                if( attackType == PokemonType.POISON || attackType == PokemonType.STEEL)
                    modifier = 2;
                else if( attackType == PokemonType.FIGHT || attackType == PokemonType.BUG || attackType == PokemonType.DARK )
                    modifier = 0.5f;
                else if( attackType == PokemonType.DRAGON)
                    modifier = 0;
                break;
            }
            case NULL:
                modifier = 1;
                break;
        }

        return modifier;
    }

    public int getDamage(Character attacker, Character defender){

        final int damage = (int) (((( attacker.getAttack() / defender.getDefense() ) / 50)+2) * getModifier(defender.getType1(), attacker.getType1()) * getModifier(defender.getType2(), attacker.getType1()));
        return damage;
    }

    public String getEffective(Character attacker, Character defender){

        String effect = "";
        float modifier = getModifier(defender.getType1(), attacker.getType1()) * getModifier(defender.getType2(), attacker.getType1());
        if(modifier >= 2){
            effect = "it's supereffective";
        }else if( modifier < 1 && modifier != 0){
            effect = "it's not very effective";
        }else if( modifier == 0){
            effect = "it's immune";
        }
        return effect;
    }

}
