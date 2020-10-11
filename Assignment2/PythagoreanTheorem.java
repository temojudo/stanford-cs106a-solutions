
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem.");
		int a = readInt("a: ");
		int b = readInt("b: ");
		if (a <= 0 || b <= 0) {
			println("Egdes of triangle must be positive number");
		} else {
			double d = a * a + b * b;
			double c = Math.sqrt(d);
			println("C = " + c);
		}
	}
}
