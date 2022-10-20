package com.mygdx.game.Command;

import com.mygdx.game.Dialogue.DialogueController;
import com.mygdx.game.Dialogue.DialogueNode.NODE_TYPE;

public class DialogueSelectCommand implements Command {

    DialogueController dialogueController;

    public DialogueSelectCommand(DialogueController dialogueController) {
        this.dialogueController = dialogueController;
    }

    @Override
    public void execute() {
        if (!dialogueController.optionBox.isVisible()) {
            dialogueController.moove = true;
        }
        dialogueController.hasChosen = false;

        if (dialogueController.traverser != null && dialogueController.dialogueBox.isFinished()) {
            if (dialogueController.traverser.getType() == NODE_TYPE.END) {
                dialogueController.traverser = null;
                dialogueController.dialogueBox.setVisible(false);
            } else if (dialogueController.traverser.getType() == NODE_TYPE.LINEAR) {
                dialogueController.progress(0);
            } else if (dialogueController.traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
                dialogueController.progress(dialogueController.optionBox.getIndex());
                if (dialogueController.moove == true) {
                    dialogueController.hasChosen = true;
                }
            }
        }

    }

}
