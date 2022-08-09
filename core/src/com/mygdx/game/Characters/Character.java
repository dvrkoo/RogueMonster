package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    CharacterState state;
    int counter = 0;

    //to use this method we need to insert 1/-1/0 values
    // this method change the position of the character: up 0,1 down 0,-1 left -1,0 right 1,0 
    void move(float x, float y){
        float posX = this.getX();
        float posY = this.getY();
        this.setPosition(posX + x*speed, posY + y*speed);
        if(this.getX() < 0){
            this.setPosition(0, posY + y*speed);
        }else if(this.getX() > 1000-64){
            this.setPosition(1000-64, posY + y*speed);
        }else if (this.getY() < 0) {
            this.setPosition(posX + x*speed, 0);
            
        }else if (this.getY() > 1000-64) {
            this.setPosition(posX + x*speed, 1000-64);
        }
       
    }
    public void movement(float x, float y, CharacterState state){ 
        
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
                    case STANDING:{
                        break;
                    }

                }
                break;

            }
        }
        

    }
   
    Vector2 getDirection(CharacterState state){
        Vector2 dir = new Vector2();
        switch(state){
            case NORTH:{
                dir.set(0, 1);
                break;   
            }
            case WEST:{
                dir.set(-1, 0);
                break;   
            }
            case SOUTH:{
                dir.set(0, -1);
                break;   
            }
            case EAST:{
                dir.set(1, 0);
                break;   
            }
            case STANDING:{
                dir.set(0, 0);
                break;   
            }
        }
        return dir;

    }
    public Animation<TextureRegion> getAnimation(){
        return animation;

    }
    public void update(){
        
        if(counter == 0){
            this.state = CharacterState.randomDirection(); 
            counter = 30;
        }else if(counter != 0){
            counter--;
        }
        Vector2 pos = getDirection(state);
        this.movement(pos.x,pos.y, state);

    }
}
