
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

public class BreakoutExtension extends GraphicsProgram {

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

	private static final double PAUSE_TIME = 12;
	private static final double MESSAGE_SEP = 2;
	private static final double HEART_WIDTH = 20;
	private static final double HEART_HEIGHT = 20;
	private static final double HEART_SEP = 2;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");

	private double vx, vy = 3.0;
	private GRect paddle;
	private GOval ball;
	private GLabel score;
	GImage[] heart = new GImage[NTURNS];

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

		// first add bricks and hearts
		createBricks();
		createHearts();

		// now add ball and paddle
		addMovingPaddle();
		addBall();

		addScore();

		startGame();
	}

	// Add score and then change it when brick will be removed
	private void addScore() {
		score = new GLabel("" + 0);
		score.setColor(Color.RED);
		score.setFont("Helvetica-20");
		score.setLocation(WIDTH - score.getWidth(), score.getAscent());
		add(score);

	}

	// Add as many hearts as we have lives in this game
	private void createHearts() {
		for (int i = 0; i < NTURNS; i++) {
			heart[i] = new GImage("heart.png", i * (HEART_WIDTH + HEART_SEP), 0);
			heart[i].setSize(HEART_WIDTH, HEART_HEIGHT);
			add(heart[i]);

		}
	}

	// In this method we start playing game with if and while loops
	private void startGame() {
		int removedBricks = 0;
		int points = 0;
		for (int i = 0; i < NTURNS; i++) {

			waitForClick();
			ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);

			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) {
				vx = -vx;
			}

			while (true) {

				ball.move(vx, vy);
				pause(PAUSE_TIME);

				reboundFromWalls();
				if (ball.getY() + 2 * BALL_RADIUS >= HEIGHT) {
					remove(heart[NTURNS - i - 1]);
					break;
				}

				GObject collider = getCollidingObject();

				if (collider == paddle) {
					bounceClip.play();
					reboundFromPaddle();
				}
				if (isBrick(collider)) {

					bounceClip.play();
					reboundFromBrick(collider);
					points += addPoints(collider);

					if (collider.getColor() == Color.CYAN) {
						remove(collider);
						removedBricks++;

					} else {
						collider.setColor(changeBrickColor(collider));
					}

					score.setLabel("" + points);
					score.setLocation(WIDTH - score.getWidth(), score.getAscent());

					changeBackground(removedBricks);
					changeVy(removedBricks);
				}

				if (removedBricks == NBRICKS_PER_ROW * NBRICK_ROWS) {
					winningMessage(points);
					break;
				}

			}
			if (removedBricks == NBRICKS_PER_ROW * NBRICK_ROWS) {
				break;
			}
		}
		if (removedBricks != NBRICKS_PER_ROW * NBRICK_ROWS) {
			loosingMessage(points);
		}
	}

	// Change vy by adding vy/10 after every 10 bricks
	private void changeVy(int removedBricks) {
		if (removedBricks % 10 == 0) {
			vy += vy / 10;
		}

	}

	// To be move interesting game each colour means how many ball it needs to
	// remove brick
	private Color changeBrickColor(GObject collider) {
		if (collider.getColor() == Color.RED) {
			return Color.ORANGE;
		}
		if (collider.getColor() == Color.ORANGE) {
			return Color.YELLOW;
		}
		if (collider.getColor() == Color.YELLOW) {
			return Color.GREEN;
		}
		return Color.CYAN;

	}

	// Check colliding object if it is brick
	private boolean isBrick(GObject collider) {
		if (collider == null || collider == paddle || collider == score) {
			return false;
		}
		for (int i = 0; i < NTURNS; i++) {
			if (collider == heart[i]) {
				return false;
			}
		}
		return true;
	}

	private void reboundFromWalls() {
		if (ball.getY() <= 0) {
			vy = -vy;
		}
		if (ball.getX() + 2 * BALL_RADIUS >= WIDTH || ball.getX() <= 0) {
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

		// change vx depending on where ball touches paddle
		double x = Math.abs(ball.getX() + BALL_RADIUS - (paddle.getX() + PADDLE_WIDTH / 2));
		x /= PADDLE_WIDTH;

		if (vx > 0) {
			if (ball.getX() + BALL_RADIUS > paddle.getX() + PADDLE_WIDTH / 2) {
				vx += vx * x;
			} else {
				vx -= vx * x;
			}
		} else if (ball.getX() + BALL_RADIUS > paddle.getX() + PADDLE_WIDTH / 2) {
			vx -= vx * x;
		} else {
			vx += vx * x;
		}

		if (vx >= 0) {
			vx = Math.max(vx, 1);
		}
		if (vx < 0) {
			vx = Math.min(vx, -1);
		}

	}

	// Change background after 25,50, and 75 removing bricks
	private void changeBackground(int removedBricks) {
		if (removedBricks == NBRICKS_PER_ROW * NBRICK_ROWS / 4) {
			setBackground(Color.LIGHT_GRAY);
		}
		if (removedBricks == NBRICKS_PER_ROW * NBRICK_ROWS / 2) {
			setBackground(Color.GRAY);
		}
		if (removedBricks == 3 * NBRICKS_PER_ROW * NBRICK_ROWS / 4) {
			setBackground(Color.DARK_GRAY);
		}

	}

	// Each colour means different point
	private int addPoints(GObject collider) {
		if (collider.getColor() == Color.RED) {
			return 5;
		}
		if (collider.getColor() == Color.ORANGE) {
			return 4;
		}
		if (collider.getColor() == Color.YELLOW) {
			return 3;
		}
		if (collider.getColor() == Color.GREEN) {
			return 2;
		}
		return 1;
	}

	// This method will be showed when player looses game
	private void loosingMessage(int point) {
		GLabel loose = new GLabel("Game over");
		loose.setFont("Helvetica-30");
		loose.setColor(Color.RED);

		GLabel score = new GLabel("Your point is " + point);
		score.setFont("Helvetica-22");
		score.setColor(Color.BLUE);

		double looseWidth = loose.getWidth();
		double scoreWidth = score.getWidth();
		double scoreAscent = score.getAscent();

		// add label in the centre
		loose.setLocation((WIDTH - looseWidth) / 2, HEIGHT / 2 - MESSAGE_SEP / 2);
		score.setLocation((WIDTH - scoreWidth) / 2, HEIGHT / 2 + scoreAscent + MESSAGE_SEP / 2);
		add(loose);
		add(score);
	}

	// This method will be showed when player wins game
	private void winningMessage(int point) {
		GLabel win = new GLabel("You are winner");
		win.setFont("Helvetica-30");
		win.setColor(Color.RED);

		GLabel score = new GLabel("Your point is " + point);
		score.setFont("Helvetica-22");
		score.setColor(Color.BLUE);

		double winWidth = win.getWidth();
		double scoreWidth = score.getWidth();
		double scoreAscent = score.getAscent();

		// add label in the centre
		win.setLocation((WIDTH - winWidth) / 2, HEIGHT / 2 - MESSAGE_SEP / 2);
		score.setLocation((WIDTH - scoreWidth) / 2, HEIGHT / 2 + scoreAscent + MESSAGE_SEP / 2);
		add(win);
		add(score);
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
		GObject collider = null;
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			collider = getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			collider = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		}

		return collider;
	}

}
