/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		moveToMagazine();
		takeMagazine();
		returnBack();
	}
	//pre-condition: 3,4 looking east
	//post-condition: 6,3 looking east
	private void moveToMagazine() {
		turnRight();
		move();
		turnLeft();
		while (noBeepersPresent()) {
			move();
		}
	}
	private void takeMagazine() {
		pickBeeper();
	}
	//pre-condition: 6,3 looking east
	//post-condition: 3,4 looking east
	private void returnBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnRight();
		move();
		turnRight();
	}

}
