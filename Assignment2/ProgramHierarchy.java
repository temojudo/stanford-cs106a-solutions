
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {

	private static final double length = 140;
	private static final double height = 40;

	// d1-distance between rect1,rect2 and rect3
	private static final double d1 = 30;
	// d2-distance between rect0 and rect2
	private static final double d2 = 50;

	/* description: create graphic from centre 
	 * adding same length west and east
	 * also same length north and south
	 * by this way graphic will be in centre
	 */
	public void run() {

		// first create all rectangles
		addAllRectangles();
		
		//now add lines
		addLines();
		
		// finally add texts
		addTexts();

	}

	private void addTexts() {
		double MiddleX = (double) getWidth() / 2;
		double MiddleY = (double) getHeight() / 2;
		
		GLabel label0 = new GLabel("Program");
		double a = label0.getWidth();
		double b = label0.getAscent();
		label0.move(MiddleX - a / 2, MiddleY - d2 / 2 - (height - b) / 2);
		add(label0);

		GLabel label1 = new GLabel("GraphicsProgram");
		a = label1.getWidth();
		b = label1.getAscent();
		label1.move(MiddleX - length - length / 2 - d1 + (length - a) / 2, MiddleY + d2 / 2 + (height - b) / 2 + b);
		add(label1);

		GLabel label2 = new GLabel("ConsoleProgram");
		a = label2.getWidth();
		b = label2.getAscent();
		label2.move(MiddleX - a / 2, MiddleY + d2 / 2 + (height - b) / 2 + b);
		add(label2);

		GLabel label3 = new GLabel("DialogProgram");
		a = label3.getWidth();
		b = label3.getAscent();
		label3.move(MiddleX + length / 2 + d1 + (length - a) / 2, MiddleY + d2 / 2 + (height - b) / 2 + b);
		add(label3);

	}

	private void addLines() {
		double MiddleX = (double) getWidth() / 2;
		double MiddleY = (double) getHeight() / 2;

		GLine line1 = new GLine(MiddleX, MiddleY - d2 / 2, MiddleX - (length + d1), MiddleY + d2 / 2);
		add(line1);

		GLine line2 = new GLine(MiddleX, MiddleY - d2 / 2, MiddleX, MiddleY + d2 / 2);
		add(line2);

		GLine line3 = new GLine(MiddleX, MiddleY - d2 / 2, MiddleX + length + d1, MiddleY + d2 / 2);
		add(line3);

	}

	private void addAllRectangles() {
		double MiddleX = (double) getWidth() / 2;
		double MiddleY = (double) getHeight() / 2;

		GRect rect0 = new GRect(MiddleX - length / 2, MiddleY - d2 / 2 - height, length, height);
		add(rect0);

		GRect rect1 = new GRect(MiddleX - (length + length / 2 + d1), MiddleY + d2 / 2, length, height);
		add(rect1);

		GRect rect2 = new GRect(MiddleX - length / 2, MiddleY + d2 / 2, length, height);
		add(rect2);

		GRect rect3 = new GRect(MiddleX + length / 2 + d1, MiddleY + d2 / 2, length, height);
		add(rect3);

		
	}
}
