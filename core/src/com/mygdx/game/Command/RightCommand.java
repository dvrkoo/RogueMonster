package com.mygdx.game.Command;

import com.mygdx.game.Characters.Player;
import com.mygdx.game.Utils.Enums.CharacterState;

public class RightCommand implements Command{
    Player player;

    public RightCommand(Player player){
        this.player = player;
    }

    @Override
    public void execute() {
        player.movement(1,0, CharacterState.EAST);
        
    }
    
}
