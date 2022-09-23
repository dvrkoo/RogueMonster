package com.mygdx.game.Observers;

import com.mygdx.game.States.StartingState;
import com.mygdx.game.Utils.Enums.Pokemon;

public class StarterObserver implements Observer {
    Pokemon pkmn;
    StartingState startingState;

    public void Observer(StartingState startingState) {
        this.startingState = startingState;

    }

    @Override
    public void update(Object o) {
        this.pkmn = (Pokemon) o;
    }

    public Pokemon getPkmn() {
        return pkmn;
    }

}
