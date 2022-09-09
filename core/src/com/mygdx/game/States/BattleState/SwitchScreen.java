package com.mygdx.game.States.BattleState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Characters.Player;

public class SwitchScreen {
    public Rectangle[] buttons = new Rectangle[6];
    public Texture buttonTexture = new Texture("Buttons/button.png");
    public TextureRegion[] pokemonIcon = new TextureRegion[6];
    public boolean isVisible = false;

    Player player;

    public SwitchScreen( Player player){
        this.player = player;
        makeButtons();
        setButtons();
        makeIcon();

    }

    void makeIcon(){
        for (int i = 0; i < pokemonIcon.length; i++) {
            if(player.getPokemon(i) != null){
                final String nameString = player.getPokemon(i).getName() + ".png";
                final Texture texture = new Texture(nameString); 
                pokemonIcon[i] = new TextureRegion(texture, 0, 0, 64, 64);
            }
            
        }
    }

    public void swapIcon(int one, int two){
        final TextureRegion tmp = this.pokemonIcon[one];
        this.pokemonIcon[one] = this.pokemonIcon[two];
        this.pokemonIcon[two] = tmp;
    }

    void makeButtons(){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Rectangle();
        }
    }

    void setButtons(){
        for(int i = 0; i < buttons.length ; i++){
            buttons[i].setPosition(900, 1000 - 64 - 160*i);
            buttons[i].height = 64;
            buttons[i].width = 64;
        }
    }
    
}
