package com.mygdx.game.Observers;

import com.mygdx.game.Utils.Enums.Pokemon;

public class StarterObserver implements Observer {
    Pokemon pkmn;

    public void Observer() {

    }

    @Override
    public void update(Object o) {
        this.pkmn = (Pokemon) o;
    }

    public Pokemon getPkmn() {
        return pkmn;
    }

}
