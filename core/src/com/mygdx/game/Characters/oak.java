package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Utils.CharacterAnimation;
import com.mygdx.game.Utils.Enums.CharacterState;

public class oak extends Character {
    public oak() {

        this.setSize(64, 64);
        this.movSpeed = 2;
        Vector2 pos = new Vector2();
        pos.x = 150;
        pos.y = 300;
        texture = new Texture(Gdx.files.internal("sycamore.png"));
        region = new TextureRegion(texture, 0, 0, 64, 64);
        animation = new Animation<TextureRegion>(1f / 60f, region);
        stateBefore = CharacterState.SOUTH;
        state = CharacterState.SOUTH;
        this.setPosition(pos);
        anim = new CharacterAnimation(texture);
    }

}
