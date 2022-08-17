package com.mygdx.game.entity;

import com.mygdx.game.Utils.Enums.EntityType;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Maps.Media;

public class Tree extends Entity {

    public Tree(Vector2 pos) {
        super();
        type = EntityType.TREE;
        width = 40;
        height = 40;
        this.pos = pos;
        texture = Media.tree;

    }
    public boolean isNotPassable() {
        return true;
    }
}