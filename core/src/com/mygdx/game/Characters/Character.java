package com.mygdx.game.Characters;

import com.mygdx.game.Utils.Enums;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Maps.Collision;
import com.mygdx.game.Maps.CollisionObserver;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.Maps.Tile;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.Enums.TILETYPE;

public class Character extends Rectangle {
    // attributes
    Boolean collision;
    List<Collision> observers = new ArrayList<>();

    int speed;
    TextureRegion region;
    Texture texture;
    CharacterAnimation anim;
    Animation<TextureRegion> animation;
    CharacterState stateBefore;
    CharacterState state;
    int counter = 0;
    TILETYPE type = TILETYPE.WATER;

    // to use this method we need to insert 1/-1/0 values
    // this method change the position of the character: up 0,1 down 0,-1 left -1,0
    // right 1,0
    void move(float x, float y) {
        Vector2 pos = new Vector2();
        float posX = this.getX();
        float posY = this.getY();
        pos.x = posX;
        pos.y = posY;
        this.setPosition(posX + x * speed, posY + y * speed);
        for (Vector2 element : Island.collisions) {
            if (pos.x >= element.x - 40 && pos.x <= element.x + 40 && pos.y >= element.y - 40
                    && pos.y <= element.y + 40) {
                if (pos.x > element.x) {
                    this.setPosition(pos.x + 20, pos.y);
                    notifyObservers();
                }
                if (pos.x < element.x) {
                    this.setPosition(pos.x - 20, pos.y);
                    notifyObservers();
                }
                if (pos.y > element.y) {
                    this.setPosition(pos.x, pos.y + 20);
                    notifyObservers();
                }
                if (pos.y < element.y) {
                    this.setPosition(pos.x, pos.y - 20);
                    notifyObservers();
                }

            }
        }
        /*
         * if (this.getX() < 0) {
         * notifyObservers();
         * this.setPosition(0, posY + y * speed);
         * } else if (this.getX() > 1000 - 64) {
         * this.setPosition(1000 - 64, posY + y * speed);
         * } else if (this.getY() < 0) {
         * this.setPosition(posX + x * speed, 0);
         * 
         * } else if (this.getY() > 1000 - 64) {
         * this.setPosition(posX + x * speed, 1000 - 64);
         * }
         */

    }

    public void addObserver(Collision obs) {
        observers.add(obs);
    }

    public void removeObserver(Collision obs) {
        observers.remove(obs);
    }

    private void notifyObservers() {
        for (Collision obs : observers) {
            obs.update(true);
        }
    }

    public void movement(float x, float y, CharacterState state) {

        move(x, y);
        switch (state) {
            case NORTH: {
                animation = anim.getNorthAnimation();
                this.stateBefore = state;
                break;

            }
            case WEST: {
                animation = anim.getWestAnimation();
                this.stateBefore = state;
                break;

            }
            case SOUTH: {
                animation = anim.getSouthAnimation();
                this.stateBefore = state;
                break;

            }
            case EAST: {
                animation = anim.getEastAnimation();
                this.stateBefore = state;
                break;

            }
            case STANDING: {
                switch (stateBefore) {

                    case NORTH: {
                        region.setRegion(0, 64 * 3, 64, 64);
                        animation = new Animation<TextureRegion>(1f / 60f, region);
                        break;

                    }
                    case WEST: {
                        region.setRegion(0, 64, 64, 64);
                        animation = new Animation<TextureRegion>(1f / 60f, region);
                        break;
                    }
                    case EAST: {
                        region.setRegion(0, 64 * 2, 64, 64);
                        animation = new Animation<TextureRegion>(1f / 60f, region);
                        break;

                    }
                    case SOUTH: {
                        region.setRegion(0, 64 * 0, 64, 64);
                        animation = new Animation<TextureRegion>(1f / 60f, region);
                        break;
                    }
                    case STANDING: {
                        break;
                    }

                }
                break;

            }
        }

    }

    Vector2 getDirection(CharacterState state) {
        Vector2 dir = new Vector2();
        switch (state) {
            case NORTH: {
                dir.set(0, 1);
                break;
            }
            case WEST: {
                dir.set(-1, 0);
                break;
            }
            case SOUTH: {
                dir.set(0, -1);
                break;
            }
            case EAST: {
                dir.set(1, 0);
                break;
            }
            case STANDING: {
                dir.set(0, 0);
                break;
            }
        }
        return dir;

    }

    public Animation<TextureRegion> getAnimation() {
        return animation;

    }

    public Vector2 pos;

    public void update() {

        if (counter == 0) {
            this.state = CharacterState.randomDirection();
            counter = 30;
        } else if (counter != 0) {
            counter--;
        }
        pos = getDirection(state);
        this.movement(pos.x, pos.y, state);

    }
}
