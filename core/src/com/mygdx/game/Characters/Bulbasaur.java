package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Bulbasaur extends Character{
    
    

    public Bulbasaur(){
        //init pokemon
        
        this.setSize(64, 64);
        this.movSpeed = 2;
        type = PokemonType.GRASS;
        
        texture = new Texture(Gdx.files.internal("bulbasaur.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;
        state = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);

        //init stat
        this.hp = 13;
        this.actualHp = this.hp;
        this.attack = 13;
        this.defense = 12;
        this.speed = 12;
        this.name = "bulbasaur";
    }
    
}
