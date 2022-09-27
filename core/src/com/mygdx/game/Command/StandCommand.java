package com.mygdx.game.Command;

import com.mygdx.game.Characters.Player;
import com.mygdx.game.Utils.Enums.CharacterState;

public class StandCommand implements Command{

    Player player;

    public StandCommand(Player player){
        this.player = player;
    }

    @Override
    public void execute() {
        player.movement(0, 0, CharacterState.STANDING);
    }
    
}
