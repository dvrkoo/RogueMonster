package com.mygdx.game.Command;

import com.mygdx.game.Characters.Player;
import com.mygdx.game.Utils.Enums.CharacterState;

public class DownCommand implements Command{
    Player player;

    public DownCommand(Player player){
        this.player = player;
    }

    @Override
    public void execute() {
        player.movement(0, -1, CharacterState.SOUTH);
        
    }
    
}
