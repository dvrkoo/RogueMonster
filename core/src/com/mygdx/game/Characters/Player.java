package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Utils.CharacterAnimation;

public class Player extends Character {

    private Character[] team;

    public Player(int x, int y) {
        //init player
        this.setPosition(x, y);
        this.setSize(64, 64);
        this.movSpeed = 5;
        stateBefore = CharacterState.SOUTH;

        texture = new Texture(Gdx.files.internal("PokemonPG.png"));
        region = new TextureRegion(texture, 0, 0, 64, 64);
        animation = new Animation<TextureRegion>(1f / 60f, region);

        anim = new CharacterAnimation(texture);

        //init team
        team = new Character[6];
    }

    public void commandMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {// down
            this.movement(0, -1, CharacterState.SOUTH);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {// left
            this.movement(-1, 0, CharacterState.WEST);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {// right
            this.movement(1, 0, CharacterState.EAST);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {// up
            this.movement(0, 1, CharacterState.NORTH);
        } else {
            this.movement(0, 0, CharacterState.STANDING);
        }

    }

    public void addPokemon(final Character pokemon){
        for(int i = 0; i < team.length; i++){
            if(team[i] == null){
                team[i] = pokemon;
            }
        }
        
    }
    public Character getFirst(){
        return team[1];
        
    }
}
