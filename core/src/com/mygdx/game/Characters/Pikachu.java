package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.CharacterState;
import com.mygdx.game.Utils.PokemonType;

public class Pikachu extends Character 
{
    PokemonType type;
    

    public Pikachu(int x, int y){
        this.setPosition( x, y);
        this.setSize(64, 64);
        this.speed = 2;
        type = PokemonType.ELECTRIC;
        
        texture = new Texture(Gdx.files.internal("pikachu.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        stateBefore = CharacterState.SOUTH;

        anim = new CharacterAnimation(texture);
    }
    
}
