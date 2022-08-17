package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utils.Enums.EntityType;

public class Entity {
    public Vector2 pos;
    public Texture texture;
    public float width;
    public float height;
    public EntityType type;

    float dir_x = 0;
    float dir_y = 0;

    public Entity() {
        pos = new Vector2();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

}