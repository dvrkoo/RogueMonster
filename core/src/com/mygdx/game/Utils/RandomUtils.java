package com.mygdx.game.Utils;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class RandomUtils {

    public Vector2 getRandomPos(Vector2 minMaxX, Vector2 minMaxY){
        Random rand = new Random();
        
        Vector2 position = new Vector2();

        position.x = minMaxX.x + rand.nextFloat() * ( minMaxX.y - minMaxX.x);
        position.y = minMaxY.x + rand.nextFloat() * ( minMaxY.y - minMaxY.x);
        return position;

    }
    
}
