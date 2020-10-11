
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private static final double PAUSE_TIME = 15;
	private static final double WAIT_FOR_BALL = 150;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private double vx, vy;
	private GRect paddle;
	private GOval ball;

	public void mouseMoved(MouseEvent e) {
		double lastX = e.getX() - PADDLE_WIDTH / 2;
		if (lastX > WIDTH - PADDLE_WIDTH) {
			lastX = WIDTH - PADDLE_WIDTH;
		}
		if (lastX < 0) {
			lastX = 0;
		}
		paddle.move(lastX - paddle.getX(), 0);

	}

	/* Method: init() */
	/** Runs the Breakout program. */
	public void init() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);

		// first add bricks
		createBricks();

		// now add ball and paddle
		addMovingPaddle();
		addBall();

		startGame();

	}

	// In this method we start playing game with if and while loops
	private void startGame() {
		int removedBricks = 0;
		for (int i = 0; i < NTURNS; i++) {
			ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
			pause(WAIT_FOR_BALL);
			vy = 3.0;
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) {
				vx = -vx;
			}

			while (true) {
				ball.move(vx, vy);
				GObject collider = getCollidingObject();
				if (collider == paddle) {
					reboundFromPaddle();
				} else if (collider != null) {
					reboundFromBrick(collider);
					remove(collider);
					removedBricks++;
				}
				reboundFromWalls();
				if (ball.getY() + 2 * BALL_RADIUS >= HEIGHT) {
					break;
				}
				if (removedBricks == NBRICKS_PER_ROW * NBRICK_ROWS) {
					winningMessage();
					break;
				}
				pause(PAUSE_TIME);
			}
			if (removedBricks == NBRICKS_PER_ROW * NBRICK_ROWS) {
				break;
			}
		}
		if (removedBricks != NBRICKS_PER_ROW * NBRICK_ROWS) {
			loosingMessage();
		}
	}

	// This method will be showed when player looses game
	private void loosingMessage() {
		GLabel loose = new GLabel("Game over");
		loose.setFont("Helvetica-30");
		loose.setColor(Color.RED);
		double width = loose.getWidth();
		double ascent = loose.getAscent();

		// add label in the centre
		loose.setLocation((WIDTH - width) / 2, (HEIGHT - ascent) / 2 + ascent);
		add(loose);

	}

	// This method will be showed when player wins game
	private void winningMessage() {
		GLabel win = new GLabel("You won");
		win.setFont("Halveta-30");
		win.setColor(Color.GREEN);
		double width = win.getWidth();
		double ascent = win.getAscent();

		// add label in the centre
		win.setLocation((WIDTH - width) / 2, (HEIGHT - ascent) / 2 + ascent);
		add(win);
	}

	// create circle with centre coordinates
	private GOval createFilledCircle(double x, double y, double r, Color color) {
		GOval circle = new GOval(x - r, y - r, 2 * r, 2 * r);
		circle.setFilled(true);
		circle.setColor(color);
		return circle;
	}

	private void addBall() {
		ball = createFilledCircle(WIDTH / 2, HEIGHT / 2, BALL_RADIUS, Color.BLACK);
		add(ball);
	}

	// create paddle with mouse listeners
	private void addMovingPaddle() {
		paddle = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}

	// Add all brick in canvas
	private void createBricks() {
		double rowOfBricks = BRICK_Y_OFFSET;
		for (int i = 0; i < NBRICK_ROWS; i++) {
			double brickInRow = (WIDTH - NBRICKS_PER_ROW * BRICK_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				GRect brick = creatFilledRect(brickInRow, rowOfBricks, BRICK_WIDTH, BRICK_HEIGHT, i);
				add(brick);
				brickInRow += BRICK_WIDTH + BRICK_SEP;
			}
			rowOfBricks += BRICK_HEIGHT + BRICK_SEP;
		}
	}

	// create brick with colour
	private GRect creatFilledRect(double x, double y, double width, double height, int numberOfLine) {
		GRect rect = new GRect(x, y, width, height);
		rect.setFilled(true);
		if (numberOfLine % NBRICK_ROWS < 2) {
			rect.setColor(Color.RED);
		} else if (numberOfLine % NBRICK_ROWS < 4) {
			rect.setColor(Color.ORANGE);
		} else if (numberOfLine % NBRICK_ROWS < 6) {
			rect.setColor(Color.YELLOW);
		} else if (numberOfLine % NBRICK_ROWS < 8) {
			rect.setColor(Color.GREEN);
		} else {
			rect.setColor(Color.CYAN);
		}
		return rect;
	}
	
	// Check four coordinates near ball and take object
	private GObject getCollidingObject() {
		GObject gobj = null;
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			gobj = getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			gobj = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			gobj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		}
		return gobj;
	}

	private void reboundFromPaddle() {
		if (ball.getY() + BALL_RADIUS <= paddle.getY()) {
			vy = -Math.abs(vy);
		}
		if (vx < 0 && ball.getX() + BALL_RADIUS >= paddle.getX() + PADDLE_WIDTH) {
			vx = -vx;
		}

		if (vx > 0 && ball.getX() + BALL_RADIUS <= paddle.getX()) {
			vx = -vx;
		}
	}

	private void reboundFromBrick(GObject collider) {
		if ((vx > 0 && ball.getX() + BALL_RADIUS <= collider.getX())
				|| (vx < 0 && ball.getX() + BALL_RADIUS >= collider.getX() + collider.getWidth())) {
			vx = -vx;
		}
		if (ball.getY() + BALL_RADIUS >= collider.getY() + collider.getHeight()
				|| ball.getY() + BALL_RADIUS <= collider.getY()) {
			vy = -vy;
		}
	}

	private void reboundFromWalls() {
		if (ball.getY() <= 0) {
			vy = -vy;
		}
		if (ball.getX() + 2 * BALL_RADIUS >= WIDTH || ball.getX() <= 0) {
			vx = -vx;
		}

	}

}
