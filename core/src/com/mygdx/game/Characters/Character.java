package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Character extends Rectangle{
    //attributes

    private int speed;
    TextureRegion region;
    

    


    //methods
    
    public Character(int x, int y){
        this.speed = 5;
        this.setPosition(x, y);
        this.setSize(64, 64);
        
        
    };

    //to use this method we need to insert 1/-1/0 values
    // this method change the position of the character: up 0,1 down 0,-1 left -1,0 right 1,0 
    void move(int x, int y){
        float posX = this.getX();
        float posY = this.getY();
        this.setPosition(posX + x*speed, posY + y*speed);
    }
}

