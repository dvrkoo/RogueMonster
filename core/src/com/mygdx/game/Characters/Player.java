package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.CharacterState;
import com.mygdx.game.Utils.PlayerAnimation;

public class Player extends Character {
   
    Texture texture;
    TextureRegion standingRegion;
    PlayerAnimation anim;
    Animation<TextureRegion> animation;
    CharacterState stateBefore;
    

    public Player(int x, int y) {
        super(x, y);
        stateBefore = CharacterState.SOUTH;
        anim = new PlayerAnimation();
        texture = new Texture(Gdx.files.internal("PokemonPG.png"));
        region = new TextureRegion(texture,0,0,64,64);
        animation = new Animation<TextureRegion>(1f/60f,region);
        standingRegion = new TextureRegion();
        
        //TODO Auto-generated constructor stub
    }

    public void movement(int x, int y, CharacterState state){ 
        move(x, y);
        switch(state){
            case NORTH:{
                animation = anim.getNorthAnimation();
                this.stateBefore = state;
                break;
                
            }
            case WEST:{
                animation = anim.getWestAnimation();

                this.stateBefore = state;
                break;

            }
            case SOUTH:{
                animation = anim.getSouthAnimation();
                this.stateBefore = state;
                break;

            }
            case EAST:{
                animation = anim.getEastAnimation();
                this.stateBefore = state;   
                break;

            }
            case STANDING:{
                System.out.println(stateBefore);
                switch (stateBefore){
                    
                    case NORTH:{
                        standingRegion.setRegion(region, 0, 64*3, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,standingRegion);
                        break;

                    }
                    case WEST:{
                        standingRegion.setRegion(region, 0, 64, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,standingRegion);
                        break;
                    }
                    case EAST:{
                        standingRegion.setRegion(region, 0, 64*2, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,standingRegion);
                        break;
                        
                    }
                    case SOUTH:{
                        standingRegion.setRegion(region, 0, 64*0, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,standingRegion);
                        break;
                    }

                }
                break;

            }
        }
        

    }

    public Animation<TextureRegion> getAnimation(){
        return animation;

    }
}
