package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Character extends Sprite{
    //attributes

    private int speed;
    private int posX;
    private int posY;
    


    //methods
    
    public Character(int x, int y){
        this.speed = 10;
        this.setPosition(x, y);
        this.setSize(32, 32);
        this.setTexture(new Texture(Gdx.files.internal("Brendan.png")));
        this.setScale(3.5f);

        

    
    };

    //to use this method we need to insert 1/-1/0 values 
    void move(int x, int y){
        this.posX += (x * speed);
        this.posY += (y * speed);
        this.setPosition(posX, posY);
    }

    //getters and setters
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
}
