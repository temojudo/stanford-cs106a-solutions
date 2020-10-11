
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {

	// number when program stops
	private static final int StopComputing = 0;

	public void run() {

		int min = 0, max = 0, k = 0;
		while (true) {
			int n = readInt("? ");
			if (n == StopComputing) {
				break;
			}
//			by means of k we can understand first incoming number 
			if (k == 0) {
				min = n;
				max = n;
			} else {
				min = Math.min(min, n);
				max = Math.max(max, n);
			}
			k = 1;
		}

		if (k != 1) {
			println("There are no numbers to compute smallest and largest");
		} else {
			println("Smallest: " + min);
			println("Largest: " + max);
		}

	}
}
