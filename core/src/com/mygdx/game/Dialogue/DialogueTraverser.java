package com.mygdx.game.Dialogue;

import java.util.List;

import com.mygdx.game.Dialogue.DialogueNode.NODE_TYPE;

/**
 * @author hydrozoa
 */
public class DialogueTraverser {

	private Dialogue dialogue;
	public static DialogueNode currentNode;

	public DialogueTraverser(Dialogue dialogue) {
		this.dialogue = dialogue;
		currentNode = dialogue.getNode(dialogue.getStart());
	}

	public DialogueNode getNextNode(int pointerIndex) {
		DialogueNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
		currentNode = nextNode;
		return nextNode;
	}

	public List<String> getOptions() {
		return currentNode.getLabels();
	}

	public String getText() {
		return currentNode.getText();
	}

	public NODE_TYPE getType() {
		return currentNode.getType();
	}

}
