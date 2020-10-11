/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	public void run() {
		fillFirstLine();
		returnBack();
		
		//check if there are 3 beepers and remove 2 from east and west
		while (frontIsClear()) {
			if (beepersPresent()) {
				if (frontIsClear()) {
					move();
					if (beepersPresent()) {
						if (frontIsClear()) {
							move();
							if (beepersPresent()) {
								returnBack();
								removeTwoBeepers();
								returnBack();
							}
						}
					}
				}
			} else {
				move();
			}
		}
		
		//now there are only 1 or 2 beepers in the middle
		//remove second one
		returnBack();
		while (frontIsClear()) {
			if (beepersPresent()) {
				move();
				if (beepersPresent()) {
					pickBeeper();
					returnBack();
				}
			} else {
				move();
			}
		}
		returnBack();
		while (noBeepersPresent()) {
			move();
		}
		
	}
	private void fillFirstLine() {
		putBeeper();
		while (frontIsClear()) {
			move();
			putBeeper();
		}
	}
	//pre: stays somewhere looking east
	//post: 1,1 looking east
	private void returnBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}
	//pre: 1,1 looking east
	//post: stays somewhere looking east
	private void removeTwoBeepers() {
		while (frontIsClear()) {
			if (beepersPresent()) {
				pickBeeper();
				goToSecondSide();
				turnAround();
				//remove second beeper
				while (frontIsClear()) {
					if (beepersPresent()) {
						pickBeeper();
						while (frontIsClear()) {
							move();
						}
					} else {
						move();
					}
				}
				turnAround();
				while (frontIsClear()) {
					move();
				}
			} else {
				move();
			}
		}
	}
	//pre: stays somewhere looking east
	//post: stays at end of line looking east
	private void goToSecondSide() {
		while (frontIsClear()) {
			move();
		}
	}

}
