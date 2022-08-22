package com.mygdx.game.States.BattleState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Characters.Player;

public class SwitchScreen extends Rectangle{
    Rectangle[] buttons = new Rectangle[6];
    Texture buttonTexture = new Texture("Buttons/button.png");

    public SwitchScreen( Player player){
        

    }
    
}
