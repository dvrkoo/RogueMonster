package com.mygdx.game.Maps;

public class Collision implements CollisionObserver {

    @Override
    public void update(Boolean collision) {
        System.out.println(collision);
    }

}
