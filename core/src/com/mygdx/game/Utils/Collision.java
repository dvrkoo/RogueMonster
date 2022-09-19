package com.mygdx.game.Utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.States.GameState;
import com.mygdx.game.States.StartingState;
import com.mygdx.game.ui.DialogueBox;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Mudkip;
import com.mygdx.game.Controller.DialogueController;
import com.mygdx.game.Dialogue.Dialogue;
import com.mygdx.game.Dialogue.DialogueNode;

public class Collision {

    public boolean getStarterpokemon(Rectangle pos) {
        boolean collision = false;
        if (pos.overlaps(StartingState.Mudkip)) {
            generateDialogue("Mudkip");
            collision = true;
        } else if (pos.overlaps(StartingState.Charmander)) {
            generateDialogue("Charmander");
            collision = true;
        } else if (pos.overlaps(StartingState.Bulbasaur)) {
            generateDialogue("Bulbasaur");
            collision = true;
        }
        return collision;
    }

    public void generateDialogue(String pkmn) {
        Dialogue dialogue = new Dialogue();

        DialogueNode node1 = new DialogueNode("Are you sure you want to pick " + pkmn + "?", 0);
        DialogueNode node2 = new DialogueNode("You won't be able to choose again", 1);

        DialogueNode node3 = new DialogueNode("Good luck !", 2);
        DialogueNode node4 = new DialogueNode("Alright then", 3);

        node1.makeLinear(node2.getID());
        node2.addChoice("Yes", 2);
        node2.addChoice("No", 3);

        dialogue.addNode(node1);
        dialogue.addNode(node2);

        dialogue.addNode(node3);
        dialogue.addNode(node4);
        StartingState.dialogueController.getPkmn(pkmn);
        StartingState.dialogueController.startDialogue(dialogue);

    }

    public boolean getMapCollisions(Rectangle pos) {
        boolean collision = false;
        for (Rectangle rec : StartingState.rectangleArray) {
            if (pos.overlaps(rec)) {
                collision = true;
            }
        }
        return collision;
    }

    public boolean getCollision(Rectangle pos) {
        boolean collision = false;
        for (Rectangle element : Island.collisionRectangle) {
            if (pos.overlaps(element)) {
                collision = true;
            }
        }
        return collision;
    }

    public boolean getPkmnCollision(Rectangle pos) {
        boolean collision = false;
        if (GameState.pokemon != null) {
            for (Rectangle pkmn : GameState.pokemon) {
                if (pos.overlaps(pkmn) && pos != pkmn) {
                    collision = true;
                }
            }
        }
        return collision;
    }

    public boolean getPlayerCollision(Rectangle pos) {
        boolean collision = false;
        for (Character pkmn : GameState.pokemon) {
            if (pos.overlaps(pkmn)) {
                collision = true;
                pkmn.isOpponent = true;
            }

        }
        return collision;
    }

}
