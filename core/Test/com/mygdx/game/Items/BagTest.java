package com.mygdx.game.Items;

import com.mygdx.game.Utils.Enums;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag = new Bag();

    @Test
    void insert() {
        bag.insert(new Item(Enums.ItemType.POTION));
        assertEquals(bag.bag.get(0).get(0).getItemName(), "Potion");

    }

    @Test
    void remove() {
        bag.insert(new Item(Enums.ItemType.POTION));
        bag.remove(bag.bag.get(0).get(0));
        assertTrue(bag.bag.isEmpty());
    }

    @Test
    void getBag() {
        bag.insert(new Item(Enums.ItemType.POTION));
        assertEquals(bag.getBag().get(0).get(0).getItemName(), "Potion");
    }
}