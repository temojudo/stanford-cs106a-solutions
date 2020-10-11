
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {

	// radius of large circle
	private static final double r1 = 72;

	// radius of medium circle
	private static final double r2 = r1 * 1.65 / 2.64;

	// radius of small circle
	private static final double r3 = r1 * 0.76 / 2.64;

	private GOval createFilledCircle(double x,double y,double r,Color color) {
		GOval circle=new GOval(x-r,y-r,2*r,2*r);
		circle.setFilled(true);
		circle.setColor(color);
		return circle;
	}
	
	public void run() {
		double CentreX = getWidth() / 2;
		double CentreY = getHeight() / 2;

		// Large circle
		GOval Circle1 = createFilledCircle(CentreX, CentreY, r1,Color.RED);
		add(Circle1);

		// Medium circle
		GOval Circle2 = createFilledCircle(CentreX, CentreY, r2,Color.WHITE);
		add(Circle2);

		// Small circle
		GOval Circle3 = createFilledCircle(CentreX, CentreY, r3,Color.RED);
		add(Circle3);
	}
}
