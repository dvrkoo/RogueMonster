package com.mygdx.game.Command;

import com.mygdx.game.States.BattleState.BagScreen;

public class BagCommand implements Command{
    BagScreen bagScreen;

    public BagCommand(BagScreen bagScreen){
        this.bagScreen = bagScreen;

    }

    @Override
    public void execute() {
        bagScreen.isVisible = true;
    }
    
}
