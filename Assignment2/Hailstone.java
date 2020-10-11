
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int n = readInt("Enter a number: ");
		int k = 0;
		while (n > 1) {
			k++;
			if (n % 2 == 0) {
				println(n + " is even so I take half: " + (n / 2));
				n /= 2;
			} else {
				println(n + " is odd, so I make 3n + 1: " + (3 * n + 1));
				n = 3 * n + 1;
			}
		}
		println("The process took " + k + " to reach 1");
	}
}
