package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Mew extends Character{
    

    public Mew(){
    
        this.setSize(64, 64);
        this.movSpeed = 2;
        type1 = PokemonType.PSYCHIC;
        
        texture = new Texture(Gdx.files.internal("mew.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;
        state = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);

        //init base stats
        this.hpBase = 100;
        this.attackBase = 100;
        this.defenseBase = 100;
        this.speedBase = 100;

        //init stat
        this.level = 10;
        this.statCalculation();
        this.actualHp = this.hp;
        this.name = "mew";
    }
    
}
