package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.CharacterState;

public class Character extends Rectangle{
    //attributes

    int speed;
    TextureRegion region;
    Texture texture;
    CharacterAnimation anim;
    Animation<TextureRegion> animation;
    CharacterState stateBefore;

    //to use this method we need to insert 1/-1/0 values
    // this method change the position of the character: up 0,1 down 0,-1 left -1,0 right 1,0 
    void move(int x, int y){
        float posX = this.getX();
        float posY = this.getY();
        this.setPosition(posX + x*speed, posY + y*speed);
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
                        region.setRegion( 0, 64*3, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,region);
                        break;

                    }
                    case WEST:{
                        region.setRegion( 0, 64, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,region);
                        break;
                    }
                    case EAST:{
                        region.setRegion( 0, 64*2, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,region);
                        break;
                        
                    }
                    case SOUTH:{
                        region.setRegion( 0, 64*0, 64, 64);
                        animation = new Animation<TextureRegion>(1f/60f,region);
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

