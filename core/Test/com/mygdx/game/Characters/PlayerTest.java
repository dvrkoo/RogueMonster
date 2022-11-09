package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Items.Item;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Utils.Enums;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player = new Player(200,200);
    Mudkip mudkip = new Mudkip();

    @Test
    void move() {
        player.movement(1,0, Enums.CharacterState.WEST);
        assertEquals(player.getX(), 200 + player.movSpeed);
        assertEquals(player.getY(), 200);


    }

    @Test
    void addPokemon() {
        player.addPokemon(mudkip);
        assertEquals(player.getPokemon(0).getName(),"mudkip");
    }

    @Test
    void removePokemon() {
        player.addPokemon(mudkip);
        player.removePokemon(0);
        assertNull(player.getPokemon(0));
    }

    @Test
    void addItem() {
        player.addItem(new Item(Enums.ItemType.POTION));
        assertEquals(player.getBag().getBag().get(0).get(0).getItemName(), "potion");
    }

    @Test
    void removeItem() {
        player.addItem(new Item(Enums.ItemType.POTION));
        player.removeItem(player.getBag().getBag().get(0).get(0));
        assertNull(player.getBag().getBag().get(0).get(0));
    }

    @Test
    void swapPokemon() {
        player.addPokemon(new Mudkip());
        player.addPokemon(new Charmander());
        player.swapPokemon(0,1);
        assertEquals(player.getPokemon(0).getName(), "charmander");
    }
}