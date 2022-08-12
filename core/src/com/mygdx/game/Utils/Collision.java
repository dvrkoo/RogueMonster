package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Maps.Island;

public class Collision  {
    public boolean getCollision(Vector2 pos){
        boolean collision = false;
        for (Vector2 element : Island.collisions) {
            if (pos.x >= element.x - 40 && pos.x <= element.x + 40 && pos.y >= element.y - 40
                    && pos.y <= element.y + 40) {
                if (pos.x > element.x) {
                    collision = true;
                    //notifyObservers();
                }else if (pos.x < element.x) {
                    collision = true;
                    //notifyObservers();
                }else if (pos.y > element.y) {
                    collision = true;
                    //notifyObservers();
                }else if (pos.y < element.y) {
                    collision = true;
                    //notifyObservers();
                }

            }
            
        }

        return collision;
    }

   /*  @Override
    public void update(Object o) {
        //System.out.println(collision);
    }*/

}
