package com.mygdx.game.Observers;

import com.mygdx.game.States.BattleState.BattleBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleNotifierTest {

    @Test
    void update() {
        BattleBox batt = new BattleBox();
        BattleNotifier obs = new BattleNotifier(batt);
        obs.update("test");
        assertEquals(batt.getText(),"test");
    }
}