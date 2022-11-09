package com.mygdx.game.Items;

import com.mygdx.game.Characters.Mudkip;
import com.mygdx.game.Utils.Enums;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void itemInit() {
        Item item = new Item(Enums.ItemType.POTION);
        assertEquals(item.getItemName(),"Potion");
    }

    @Test
    void useItem() {
        Item item = new Item(Enums.ItemType.POTION);
        Mudkip mudkip = new Mudkip();
        mudkip.takeDamage(29);
        item.useItem(mudkip);
        assertEquals(mudkip.getActualHp(), 21);
    }
}