package com.mygdx.game.Controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.Dialogue.Dialogue;
import com.mygdx.game.Dialogue.DialogueNode;

import com.mygdx.game.Dialogue.DialogueTraverser;
import com.mygdx.game.Dialogue.DialogueNode.NODE_TYPE;
import com.mygdx.game.States.StartingState;
import com.mygdx.game.ui.DialogueBox;
import com.mygdx.game.ui.OptionBox;

public class DialogueController extends InputAdapter {

    Boolean check;
    private DialogueTraverser traverser;
    private DialogueBox dialogueBox;
    private OptionBox optionBox;
    Boolean moove = true;
    public String pkmn;

    public boolean isNode(DialogueNode node) {
        if (traverser.currentNode == node) {
            return true;
        } else {
            return false;
        }
    }

    public DialogueController(DialogueBox box, OptionBox optionBox) {
        this.dialogueBox = box;
        this.optionBox = optionBox;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        check = false;
        System.out.print(moove);
        if (!optionBox.isVisible()) {
            moove = true;
        }
        if (optionBox.isVisible()) {
            if (keycode == Keys.UP) {
                optionBox.moveUp();
                moove = true;
                return true;
            } else if (keycode == Keys.DOWN) {
                moove = false;
                optionBox.moveDown();
                return true;
            }
        }

        if (traverser != null && keycode == Keys.X && dialogueBox.isFinished()) { // continue through tree

            if (traverser.getType() == NODE_TYPE.END) {
                traverser = null;
                dialogueBox.setVisible(false);
            } else if (traverser.getType() == NODE_TYPE.LINEAR) {
                progress(0);
            } else if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
                progress(optionBox.getIndex());
                if (moove == true) {
                    StartingState.addPkmn(pkmn);
                }
            }
            return true;
        }
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }

    public void update(float delta) {
        if (dialogueBox.isFinished() && traverser != null) {
            if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
                optionBox.setVisible(true);
            }
        }
    }

    public void startDialogue(Dialogue dialogue) {
        traverser = new DialogueTraverser(dialogue);
        dialogueBox.setVisible(true);
        dialogueBox.animateText(traverser.getText());
        if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
            optionBox.clear();
            for (String s : dialogue.getNode(dialogue.getStart()).getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    private void progress(int index) {
        optionBox.setVisible(false);
        DialogueNode nextNode = traverser.getNextNode(index);
        dialogueBox.animateText(nextNode.getText());
        if (nextNode.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
            optionBox.clearChoices();
            for (String s : nextNode.getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    public boolean isDialogueShowing() {
        return dialogueBox.isVisible();
    }

    void setMoove() {
        moove = true;
    }
}