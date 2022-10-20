package com.mygdx.game.Command;

import com.mygdx.game.Dialogue.DialogueController;

public class DialogueDownCommand implements Command {

    DialogueController dialogueController;

    public DialogueDownCommand(DialogueController dialogueController) {
        this.dialogueController = dialogueController;
    }

    @Override
    public void execute() {
        if (!dialogueController.optionBox.isVisible()) {
            dialogueController.moove = true;
        }
        dialogueController.hasChosen = false;

        if (dialogueController.optionBox.isVisible()) {
            dialogueController.optionBox.moveDown();
            dialogueController.moove = false;
        }
    }

}
