package com.mygdx.game.Observers;

import com.mygdx.game.Characters.Player;

public class ChangeSateteNotifier implements Observer{

    Player player;

    boolean isChanged = false;

    public ChangeSateteNotifier(Player player){
        this.player = player;
    }


    @Override
    public void update(Object isChanged) {
        // TODO Auto-generated method stub
        this.isChanged = (boolean) isChanged;
        player.setIsGamestate(this.isChanged);
        
    }
}