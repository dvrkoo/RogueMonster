package com.mygdx.game.Controller;

import java.lang.StackWalker.Option;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.Dialogue.ChoiceDialogueNode;
import com.mygdx.game.Dialogue.Dialogue;
import com.mygdx.game.Dialogue.DialogueNode;
import com.mygdx.game.Dialogue.LinearDialogueNode;
import com.mygdx.game.Dialogue.DialogueTraverser;
import com.mygdx.game.ui.DialogueBox;
import com.mygdx.game.ui.OptionBox;

public class DialogueController extends InputAdapter {

    private DialogueTraverser traverser;
    private DialogueBox dialogueBox;
    private OptionBox optionBox;

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
        if (optionBox.isVisible()) {
            if (keycode == Keys.UP) {
                optionBox.moveUp();
                return true;
            } else if (keycode == Keys.DOWN) {
                optionBox.moveDown();
                return true;
            }
        }
        if (dialogueBox.isVisible() && !dialogueBox.isFinished()) {
            return false;
        }
        if (traverser != null && keycode == Keys.X) { // continue through tree
            DialogueNode thisNode = traverser.getNode();

            if (thisNode instanceof LinearDialogueNode) {
                LinearDialogueNode node = (LinearDialogueNode) thisNode;
                if (node.getPointers().isEmpty()) { // dead end, since no pointers
                    traverser = null; // end dialogue
                    dialogueBox.setVisible(false);
                } else {
                    progress(0); // progress through first pointer
                }
            }
            if (thisNode instanceof ChoiceDialogueNode) {
                ChoiceDialogueNode node = (ChoiceDialogueNode) thisNode;
                progress(optionBox.getIndex());
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
            DialogueNode nextNode = traverser.getNode();
            if (nextNode instanceof ChoiceDialogueNode) {
                optionBox.setVisible(true);
            }
        }
    }

    public void startDialogue(Dialogue dialogue) {
        traverser = new DialogueTraverser(dialogue);
        dialogueBox.setVisible(true);

        DialogueNode nextNode = traverser.getNode();
        if (nextNode instanceof LinearDialogueNode) {
            LinearDialogueNode node = (LinearDialogueNode) nextNode;
            dialogueBox.animateText(node.getText());
        }
        if (nextNode instanceof ChoiceDialogueNode) {
            ChoiceDialogueNode node = (ChoiceDialogueNode) nextNode;
            dialogueBox.animateText(node.getText());
            optionBox.clear();
            for (String s : node.getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    private void progress(int index) {
        optionBox.setVisible(false);
        DialogueNode nextNode = traverser.getNextNode(index);

        if (nextNode instanceof LinearDialogueNode) {
            LinearDialogueNode node = (LinearDialogueNode) nextNode;
            dialogueBox.animateText(node.getText());
        }
        if (nextNode instanceof ChoiceDialogueNode) {
            ChoiceDialogueNode node = (ChoiceDialogueNode) nextNode;
            dialogueBox.animateText(node.getText());
            optionBox.clearChoices();
            for (String s : node.getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    public boolean isDialogueShowing() {
        return dialogueBox.isVisible();
    }
}