package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.Command.Command;
import com.mygdx.game.Command.DialogueDownCommand;
import com.mygdx.game.Command.DialogueSelectCommand;
import com.mygdx.game.Command.DialogueUpCommand;
import com.mygdx.game.Dialogue.Dialogue;
import com.mygdx.game.Dialogue.DialogueNode;

import com.mygdx.game.Dialogue.DialogueTraverser;
import com.mygdx.game.Dialogue.DialogueNode.NODE_TYPE;
import com.mygdx.game.ui.DialogueBox;
import com.mygdx.game.ui.OptionBox;

public class DialogueController {

    public DialogueTraverser traverser;
    public DialogueBox dialogueBox;
    public OptionBox optionBox;
    public Boolean moove = true;
    public String pkmn;
    public Boolean hasChosen = false;

    Command dialogueSelectCommand;
    Command dialogueUpCommand;
    Command dialogueDownCommand;

    public DialogueController(DialogueBox box, OptionBox optionBox) {
        this.dialogueBox = box;
        this.optionBox = optionBox;
        dialogueSelectCommand = new DialogueSelectCommand(this);
        dialogueUpCommand = new DialogueUpCommand(this);
        dialogueDownCommand = new DialogueDownCommand(this);
    }

    public Boolean getHasChosen() {
        return hasChosen;
    }

    public void update(float delta) {
        if (dialogueBox.isFinished() && traverser != null) {
            if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
                optionBox.setVisible(true);
            }
        }
        commandHandle();
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

    public void progress(int index) {
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

    public Boolean choice() {
        return moove;
    }

    void setMoove() {
        moove = true;
    }

    void commandHandle() {

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {// down
            dialogueSelectCommand.execute();
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {// left
            dialogueUpCommand.execute();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {// right
            dialogueDownCommand.execute();
        }

    }
}