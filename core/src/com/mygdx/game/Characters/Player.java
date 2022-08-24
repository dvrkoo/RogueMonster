package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utils.Enums.CharacterState;

import com.mygdx.game.Utils.CharacterAnimation;

public class Player extends Character {

    private Character[] team;

    public Player(int x, int y) {
        // init player
        this.setPosition(x, y);
        this.setSize(64, 64);
        this.movSpeed = 5;
        state = CharacterState.SOUTH;
        stateBefore = CharacterState.SOUTH;

        texture = new Texture(Gdx.files.internal("PokemonPG.png"));
        region = new TextureRegion(texture, 0, 0, 64, 64);
        animation = new Animation<TextureRegion>(1f / 60f, region);

        anim = new CharacterAnimation(texture);

        // init team
        team = new Character[6];
    }

    public void commandMovement() {
        Vector2 pos = new Vector2();
        pos.x = this.getX();
        pos.y = this.getY();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {// down
            this.movement(0, -1, CharacterState.SOUTH);

            if (collision.getPkmnCollision(this) || collision.getMapCollisions(this)) {
                this.setPosition(pos);
                this.state = CharacterState.STANDING;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {// left
            this.movement(-1, 0, CharacterState.WEST);

            if (collision.getPkmnCollision(this) || collision.getMapCollisions(this)) {
                this.setPosition(pos);
                this.state = CharacterState.STANDING;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {// right
            this.movement(1, 0, CharacterState.EAST);

            if (collision.getPkmnCollision(this) || collision.getMapCollisions(this)) {
                this.setPosition(pos);
                this.state = CharacterState.STANDING;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {// up
            this.movement(0, 1, CharacterState.NORTH);

            if (collision.getPkmnCollision(this) || collision.getMapCollisions(this)) {
                this.setPosition(pos);
                this.state = CharacterState.STANDING;
            }
        } else {
            this.movement(0, 0, CharacterState.STANDING);

            if (collision.getPkmnCollision(this) || collision.getMapCollisions(this)) {
                this.setPosition(pos);
                this.state = CharacterState.STANDING;
            }
        }

    }

    public void addPokemon(final Character pokemon) {
        for (int i = 0; i < team.length; i++) {
            if (team[i] == null) {
                team[i] = pokemon;
                break;
            }
        }

    }

    public void removePokemon(int i) {
        team[i] = null;
    }

    public Character getPokemon(int i) {
        return team[i];

    }

    public void swapPokemon(final int one, final int two) {
        final Character tmp = this.team[one];
        this.team[one] = this.team[two];
        this.team[two] = tmp;
    }
}
