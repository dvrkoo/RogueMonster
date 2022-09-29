package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Metang extends Character{
    

    public Metang(){
        
        this.setSize(64, 64);
        this.movSpeed = 2;
        type1 = PokemonType.STEEL;
        type2 = PokemonType.PSYCHIC;
        
        texture = new Texture(Gdx.files.internal("metang.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;
        state = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);

        //init base stats
        this.hpBase = 60;
        this.attackBase = 75;
        this.defenseBase = 90;
        this.speedBase = 50;

        //init stat
        this.level = 10;
        this.statCalculation();
        this.actualHp = this.hp;
        this.name = "metang";
    }
    
}
