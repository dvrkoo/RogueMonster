package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Staravia extends Character{
    

    public Staravia(){
        
        this.setSize(64, 64);
        this.movSpeed = 2;
        type1 = PokemonType.FLYING;
        type2 = PokemonType.NORMAL;
        
        texture = new Texture(Gdx.files.internal("staravia.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;
        state = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);

        //init stat
        this.hp = 16;
        this.actualHp = this.hp;
        this.attack = 17;
        this.defense = 14;
        this.speed = 18;
        this.name = "staravia";
    }
    
}
