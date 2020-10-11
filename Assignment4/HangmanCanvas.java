
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private GLabel note = new GLabel("");

	/** Resets the display so that only the scaffold appears */
	private int index = 0;

	public void reset() {
		removeAll();
		note.setLabel("");
		addScaffold();
		index = 0;
	}

	private void addScaffold() {
		int width = getWidth() / 2 - BEAM_LENGTH;
		add(new GLine(width, SCAFFOLD_OFFSET_FROM_TOP, width, SCAFFOLD_HEIGHT));
		add(new GLine(width, SCAFFOLD_OFFSET_FROM_TOP, width + BEAM_LENGTH, SCAFFOLD_OFFSET_FROM_TOP));
		add(new GLine(width + BEAM_LENGTH, SCAFFOLD_OFFSET_FROM_TOP, width + BEAM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH));
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */

	public void displayWord(String word) {
		addFoundWords(word);
	}

	private GLabel label = new GLabel("");

// 	Add words and dashes in canvas	
	private void addFoundWords(String word) {
		String s = "";
		for (int i = 0; i < word.length(); i++) {
			s += word.charAt(i) + " ";
		}
		label.setLabel(s);
		label.setFont("HELVETICA-26-BOLDITALIC");
		add(label, FOUND_WORDS_FROM_LEFT, getHeight() - FOUND_WORDS_FROM_BOTTOM);

	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		addUnfoundWords(letter);
		addNewPartOfBody();
	}

//	Add parts of body depending on how many guesses is left	
	private void addNewPartOfBody() {
		if (index == 0) {
			addHead();
		}
		if (index == 1) {
			addBody();
		}
		if (index == 2) {
			addLeftHand();
		}
		if (index == 3) {
			addRightHand();
		}
		if (index == 4) {
			addLeftLeg();
		}
		if (index == 5) {
			addRightLeg();
		}
		if (index == 6) {
			addLeftFoot();
		}
		if (index == 7) {
			addRightFoot();
		}

		index = (index + 1) % 8;

	}

	private void addRightFoot() {
		GLine rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);

	}

	private void addLeftFoot() {
		GLine leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);
	}

	private void addRightLeg() {
		GLine rightHip = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(rightHip);

		GLine rightLeg = new GLine(getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightLeg);
	}

	private void addLeftLeg() {
		GLine leftHip = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(leftHip);

		GLine leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftLeg);
	}

	private void addRightHand() {
		GLine rightArm = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 + UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(rightArm);

		GLine rightHand = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 + UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(rightHand);
	}

	private void addLeftHand() {
		GLine leftArm = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 - UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(leftArm);

		GLine leftHand = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 - UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(leftHand);
	}

	private void addBody() {
		GLine body = new GLine(getWidth() / 2, SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS, getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(body);
	}

	private void addHead() {
		GOval head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head, getWidth() / 2 - HEAD_RADIUS, SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH);
	}

	private void addUnfoundWords(char letter) {
		note.setLabel(note.getLabel() + letter);
		note.setFont("HELVETICA-15-ITALIC");
		add(note, UNFOUND_WORDS_FROM_LEFT, getHeight() - UNFOUND_WORDS_FROM_BOTTOM);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int SCAFFOLD_OFFSET_FROM_TOP = 25;
	private static final int FOUND_WORDS_FROM_BOTTOM = 70;
	private static final int FOUND_WORDS_FROM_LEFT = 30;
	private static final int UNFOUND_WORDS_FROM_BOTTOM = 40;
	private static final int UNFOUND_WORDS_FROM_LEFT = 30;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
