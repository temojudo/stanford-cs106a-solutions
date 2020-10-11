/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	public void run() {
		fillAvenue();
		while (frontIsClear()) {
			moveToNextAvenue();
			fillAvenue();
		}
	}
	//pre: bottom of avenue looking east
	//post: bottom of avenue looking east
	private void fillAvenue() {
		turnLeft();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			move();
		}
		if (noBeepersPresent()) {
			putBeeper();
		}
		returnToBottomOfAvenue();
	}
	//pre: top of avenue looking north
	//post: bottom of avenue looking east
	private void returnToBottomOfAvenue() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
	private void moveToNextAvenue() {
		for (int i=0;i<4;i++) {
			move();
		}
	}


}
