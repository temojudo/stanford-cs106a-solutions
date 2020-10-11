
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.applet.AudioClip;

import acm.graphics.*;
import acm.util.MediaTools;

public class HangmanCanvasExtension extends GCanvas {

	private static final int HEART_WIDTH = 20;
	private static final int HEART_SEP = 10;
	private static final double HEART_HEIGHT = 20;
	AudioClip loseClip = MediaTools.loadAudioClip("loseMusic.au");
	AudioClip winClip = MediaTools.loadAudioClip("winMusic.au");
	private GLabel note = new GLabel("");

//	Add gif and music when you lose game
	public void losingMessage(String word) {
		removeAll();
		addFoundWords(word);

		GImage loose = new GImage("cimaka.gif");
		loose.setSize(LOOSING_P_WIDTH, LOOSING_P_HEIGHT);
		add(loose, 0, LOOSING_P_FROM_TOP);
		loseClip.play();

	}

//	Add gif and music when you win game
	public void winningMessage(String word) {
		removeAll();
		addFoundWords(word);

		GImage win = new GImage("moriarti.gif");
		win.setSize(WINNING_P_WIDTH, WINNING_P_HEIGHT);
		add(win, 0, WINNING_P_FROM_TOP);
		winClip.play();

	}

	/** Resets the display so that only the scaffold appears */
	private int index = 0;

	public void reset() {
		removeAll();
		note.setLabel("");
		addScaffold();
		createHearts();
		loseClip.stop();
		winClip.stop();
		index = 0;

	}

	GImage[] heart = new GImage[8];

	private void createHearts() {
		for (int i = 0; i < heart.length; i++) {
			heart[i] = new GImage("heart.png", i * (HEART_WIDTH + HEART_SEP), 0);
			heart[i].setSize(HEART_WIDTH, HEART_HEIGHT);
			add(heart[i]);
			heart[i].setVisible(true);

		}

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
		heart[(heart.length - index) % 8].setVisible(false);

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

// 	Add each part of body by using animation to make game more funny
//  ----------------------------------------------------------------

	private void addRightFoot() {
		GLine rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);

		for (int i = 0; i <= FOOT_LENGTH; i++) {
			rightFoot.setEndPoint(getWidth() / 2 + HIP_WIDTH + i,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			rightFoot.pause(SLOW_OTHER_PARTS);

		}

	}

	private void addLeftFoot() {
		GLine leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);

		for (int i = 0; i <= FOOT_LENGTH; i++) {
			leftFoot.setEndPoint(getWidth() / 2 - HIP_WIDTH - i,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
			leftFoot.pause(SLOW_OTHER_PARTS);

		}

	}

	private void addRightLeg() {
		GLine rightHip = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(rightHip);

		for (int i = 0; i <= HIP_WIDTH; i++) {
			rightHip.setEndPoint(getWidth() / 2 + i,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
			rightHip.pause(SLOW_OTHER_PARTS);

		}

		GLine rightLeg = new GLine(getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 + HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightLeg);

		for (int i = 0; i <= LEG_LENGTH; i++) {
			rightLeg.setEndPoint(getWidth() / 2 + HIP_WIDTH,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH - i);
			rightLeg.pause(SLOW_OTHER_PARTS);

		}

	}

	private void addLeftLeg() {
		GLine leftHip = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH, getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(leftHip);

		for (int i = 0; i <= HIP_WIDTH; i++) {
			leftHip.setEndPoint(getWidth() / 2 - i,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
			leftHip.pause(SLOW_OTHER_PARTS);

		}

		GLine leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 - HIP_WIDTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftLeg);

		for (int i = 0; i <= LEG_LENGTH; i++) {
			leftLeg.setEndPoint(getWidth() / 2 - HIP_WIDTH,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH - i);
			leftLeg.pause(SLOW_OTHER_PARTS);

		}

	}

	private void addRightHand() {
		GLine rightArm = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(rightArm);
		for (int i = 0; i <= UPPER_ARM_LENGTH; i++) {
			rightArm.setEndPoint(getWidth() / 2 + i,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
			rightArm.pause(SLOW_OTHER_PARTS);

		}

		GLine rightHand = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH,
				getWidth() / 2 + UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(rightHand);

		for (int i = 0; i <= LOWER_ARM_LENGTH; i++) {
			rightHand.setEndPoint(getWidth() / 2 + UPPER_ARM_LENGTH, SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH
					+ 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH - i);
			rightHand.pause(SLOW_OTHER_PARTS);

		}
	}

	private void addLeftHand() {
		GLine leftArm = new GLine(getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth() / 2,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(leftArm);

		for (int i = 0; i <= UPPER_ARM_LENGTH; i++) {
			leftArm.setEndPoint(getWidth() / 2 - i,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
			leftArm.pause(SLOW_OTHER_PARTS);

		}

		GLine leftHand = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH,
				getWidth() / 2 - UPPER_ARM_LENGTH,
				SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(leftHand);

		for (int i = 0; i <= LOWER_ARM_LENGTH; i++) {
			leftHand.setEndPoint(getWidth() / 2 - UPPER_ARM_LENGTH, SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH
					+ 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH - i);
			leftHand.pause(SLOW_OTHER_PARTS);

		}

	}

	private void addBody() {
		int finalY = SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + 2 * HEAD_RADIUS;
		GLine body = new GLine(1, 1, 1, 1);
		add(body);

		for (int i = 1; i <= BODY_LENGTH / 2; i++) {
			body.setStartPoint(getWidth() / 2, finalY + BODY_LENGTH / 2 - i);
			body.setEndPoint(getWidth() / 2, finalY + BODY_LENGTH / 2 + i);
			turnBody(body);

		}

	}

	private void turnBody(GLine body) {
		double centreX = getWidth() / 2;
		double bodyLength = body.getHeight();
		double centreY = body.getStartPoint().getY() + bodyLength / 2;

		body.setStartPoint(centreX - bodyLength / 2, centreY);
		body.setEndPoint(centreX - bodyLength, centreY);
		body.pause(SLOW_BODY);

		body.setStartPoint(centreX, centreY - bodyLength / 2);
		body.setEndPoint(centreX, centreY + bodyLength / 2);
		body.pause(SLOW_BODY);

		body.setStartPoint(centreX - bodyLength / 2, centreY);
		body.setEndPoint(centreX + bodyLength / 2, centreY);
		body.pause(SLOW_BODY);

		body.setStartPoint(centreX, centreY - bodyLength / 2);
		body.setEndPoint(centreX, centreY + bodyLength / 2);
		body.pause(SLOW_BODY);

	}

	private void addHead() {
		GOval head = new GOval(1, 1);
		add(head);

		for (int radius = 1; radius <= HEAD_RADIUS; radius++) {
			head.setSize(2 * radius, 2 * radius);
			head.setLocation(getWidth() / 2 - head.getWidth() / 2,
					SCAFFOLD_OFFSET_FROM_TOP + ROPE_LENGTH + HEAD_RADIUS - radius);

			head.pause(SLOW_HEAD);

		}

	}

	private void addUnfoundWords(char letter) {
		note.setLabel(note.getLabel() + letter);
		note.setFont("HELVETICA-15-ITALIC");
		add(note, UNFOUND_WORDS_FROM_LEFT, getHeight() - UNFOUND_WORDS_FROM_BOTTOM);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int SCAFFOLD_OFFSET_FROM_TOP = 35;
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
	private static final int WINNING_P_WIDTH = 377;
	private static final int WINNING_P_HEIGHT = 212;
	private static final int WINNING_P_FROM_TOP = 50;
	private static final int LOOSING_P_WIDTH = 377;
	private static final int LOOSING_P_HEIGHT = 211;
	private static final int LOOSING_P_FROM_TOP = 50;
	private static final int SLOW_HEAD = 30;
	private static final int SLOW_BODY = 5;
	private static final int SLOW_OTHER_PARTS = 12;

}
