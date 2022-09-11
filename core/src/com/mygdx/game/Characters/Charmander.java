package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Charmander extends Character{
    

    public Charmander(){
        
        this.setSize(64, 64);
        this.movSpeed = 2;
        type = PokemonType.FIRE;
        
        texture = new Texture(Gdx.files.internal("charmander.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;
        state = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);

        //init stat
        this.hp = 12;
        this.actualHp = this.hp;
        this.attack = 15;
        this.defense = 10;
        this.speed = 13;
        this.name = "charmander";
    }
    
}

