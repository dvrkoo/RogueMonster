package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Pikachu extends Character 
{
    PokemonType type;
    

    public Pikachu(){
        
        this.setSize(64, 64);
        this.movSpeed = 2;
        type = PokemonType.ELECTRIC;
        
        texture = new Texture(Gdx.files.internal("pikachu.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);

        //init stat
        this.hp = 12;
        this.attack = 12;
        this.defense = 10;
        this.speed = 16;
    }
    
}
