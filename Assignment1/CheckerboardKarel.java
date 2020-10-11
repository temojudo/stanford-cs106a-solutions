/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run() {
		fillLineFromFirst();
		returnBack();
		while (leftIsClear()) {
			if (beepersPresent()) {
				moveUp();
				fillLineFromSecond();
				returnBack();
			} else {
				moveUp();
				fillLineFromFirst();
				returnBack();
			}
		}
	}
	//move and start filling like fillLineFromFirst
	//pre: stays left looking right
	//post: stays right looking right
	private void fillLineFromSecond() {
		if (frontIsClear()) {
			move();
			fillLineFromFirst();
		}
		
	}
	//from n,m goes to n,m+1
	//before and after looking right
	private void moveUp() {
		turnLeft();
		move();
		turnRight();
	}
	//from last drawer of a line moving at first drawer
	//pre and post: looking right
	private void returnBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}
	//pre:stays at first drawer of a line looking right
	//post:stays at first drawer of a line looking right
	private void fillLineFromFirst() {
		putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}		
	}
	
}
