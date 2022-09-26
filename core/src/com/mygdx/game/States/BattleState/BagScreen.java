package com.mygdx.game.States.BattleState;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Items.Item;

public class BagScreen {
    Player player;
    public Texture buttonTexture = new Texture("Buttons/button_small.png");
    public ArrayList<Rectangle> buttons = new ArrayList<>();
    public boolean isVisible = false;

    public BagScreen(Player player){
        this.player = player;
        makeButtons();
        setButtons();

    }


    void makeButtons(){
        for (final ArrayList<Item> array : player.getBag().getBag()) {
            buttons.add(new Rectangle());
        }
            
    }
    void setButtons(){
        int counter = 0;

        for (Rectangle button : buttons) {
            button.setPosition(900, 1000 - 32 - 80 * counter);
            button.height = 32;
            button.width = 32;
            counter ++;
        }
    }

    public void drawBagScreen(RogueMonster game){
        int counter = 0;
        for (ArrayList<Item> array : player.getBag().getBag()) {
            game.font.draw(game.batch, array.get(0).getItemName() + " X " + array.size() , 770, 1000 - 10 - 80 * counter);
            counter++;
        }
        for (Rectangle button : buttons) {
            game.batch.draw(buttonTexture, button.x,button.y, button.width, button.height);
        }
    }
    
}
