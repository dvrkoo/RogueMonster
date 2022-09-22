package com.mygdx.game.Observers;

import com.mygdx.game.States.BattleState.BattleBox;

public class BattleNotifier implements Observer {

    BattleBox battleBox;

    public BattleNotifier(BattleBox battleBox) {
        this.battleBox = battleBox;
    }

    @Override
    public void update(Object toPrint) {
        battleBox.setVisible(true);
        battleBox.setText((String) toPrint);

    }
}