package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.States.GameState;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Collision;
import com.mygdx.game.Utils.Enums;
import com.mygdx.game.Utils.Enums.CharacterState;
//import com.mygdx.game.Utils.Enums.TILETYPE;
import com.mygdx.game.Utils.Enums.PokemonType;

public class Character extends Rectangle {
    // attributes

    int movSpeed;
    int attack;
    int speed;
    int defense;
    int hp;
    PokemonType type;
    String name;


    TextureRegion region;
    Texture texture;
    CharacterAnimation anim;
    Animation<TextureRegion> animation;
    CharacterState stateBefore;
    CharacterState state;
    int counter = 0;
    // TILETYPE type = TILETYPE.WATER;
    Collision collision = new Collision();

    // to use this method we need to insert 1/-1/0 values
    // this method change the position of the character: up 0,1 down 0,-1 left -1,0
    // right 1,0
    void move(float x, float y) {
        Vector2 pos = new Vector2();
        Vector2 posMov = new Vector2();
        pos.x = this.getX();
        pos.y = this.getY();
        posMov.x = pos.x + x * movSpeed;
        posMov.y = pos.y + y * movSpeed;
        this.setPosition(posMov);
        if (collision.getCollision(this)) {
            this.setPosition(pos);
            this.state = CharacterState.STANDING;
        }

    }

    public void movement(float x, float y, CharacterState state) {

        move(x, y);
        switch (state) {
            case NORTH: {
                this.stateBefore = state;
                animation = anim.getNorthAnimation();
                break;

            }
            case WEST: {
                this.stateBefore = state;
                animation = anim.getWestAnimation();
                break;

            }
            case SOUTH: {
                this.stateBefore = state;
                animation = anim.getSouthAnimation();
                break;

            }
            case EAST: {
                this.stateBefore = state;
                animation = anim.getEastAnimation();
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
        Vector2 poss = new Vector2();
        poss.x = this.getX();
        poss.y = this.getY();

        if (counter == 0) {
            this.state = CharacterState.randomDirection();
            counter = 30;
        } else if (counter != 0) {
            counter--;
        }

        pos = getDirection(this.state);
        this.movement(pos.x, pos.y, state);
        if (collision.getPkmnCollision(this)) {
            this.setPosition(poss);
            this.state = CharacterState.STANDING;
        } else if (collision.getPlayerCollision(GameState.player)) {

            this.setPosition(poss);

            this.state = CharacterState.STANDING;
        }

    }

    public void takeDamage(int damage) {

        this.hp -= damage;
    }

    // getter setter
    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public PokemonType getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    
}
