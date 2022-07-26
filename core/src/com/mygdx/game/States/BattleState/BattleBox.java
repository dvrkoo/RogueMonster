package com.mygdx.game.States.BattleState;

import com.mygdx.game.RogueMonster;

public class BattleBox {
    int x;
    int y;
    String text;

    boolean isVisible;

    public BattleBox() {
        this.x = 300;
        this.y = 300;
        text = new String();
    }

    public void draw(RogueMonster game) {
        game.font.draw(game.batch, text, x, y);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
