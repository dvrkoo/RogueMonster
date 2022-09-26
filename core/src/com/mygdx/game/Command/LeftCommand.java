package com.mygdx.game.Command;

import com.mygdx.game.Characters.Player;
import com.mygdx.game.Utils.Enums.CharacterState;

public class LeftCommand implements Command{
    Player player;

    public LeftCommand(Player player){
        this.player = player;
    }

    @Override
    public void execute() {
        player.movement(-1,0, CharacterState.WEST);
        
    }
    
}