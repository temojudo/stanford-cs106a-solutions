
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		for (int i = 0; i < N_SCORING_CATEGORIES; i++)
			for (int playerIndex = 1; playerIndex <= nPlayers; playerIndex++)
				playGameForEachPlayer(playerIndex);

		addUnfilledScores();
		winnerOfGame();

	}

//  choose one or more winner in the game depending on who has max score	
	private void winnerOfGame() {
		int maxScore = 0;
		for (int i = 1; i <= nPlayers; i++)
			if (maxScore < points[i][TOTAL])
				maxScore = points[i][TOTAL];

		String winners = "";
		for (int i = 1; i <= nPlayers; i++)
			if (points[i][TOTAL] == maxScore)
				winners += playerNames[i - 1] + ", ";

		display.printMessage(
				"Congratulations, " + winners + "you're the winner with a total score of " + maxScore + "!");

	}

//  for each player roll and reroll dice choose category and add scores	
	private void playGameForEachPlayer(int playerIndex) {
		int[] dice = new int[N_DICE];

		rollDice(dice, playerIndex);
		rerollDice(dice);
		rerollDice(dice);

		selectCategory(dice, playerIndex);

	}

//  roll dice with random generator	
	private void rollDice(int[] dice, int playerIndex) {
		display.printMessage(playerNames[playerIndex - 1] + "'s turn! Click 'Roll Dice' button to roll the dice.");
		display.waitForPlayerToClickRoll(playerIndex);

		for (int i = 0; i < N_DICE; i++)
			dice[i] = rgen.nextInt(1, 6);

		display.displayDice(dice);

	}

//	reroll selected dice	
	private void rerollDice(int[] dice) {
		display.printMessage("Select the dice you wish to re-roll and click 'Roll Again'.");
		display.waitForPlayerToSelectDice();

		for (int i = 0; i < N_DICE; i++)
			if (display.isDieSelected(i))
				dice[i] = rgen.nextInt(1, 6);

		display.displayDice(dice);

	}

//	after rerolld choose category and update total score and score card	
	private void selectCategory(int[] dice, int playerIndex) {
		display.printMessage("Select a category for this roll.");

		while (true) {
			int category = display.waitForPlayerToSelectCategory();
			if (points[playerIndex][category] == 0) {
				int score = getScore(dice, category);
				points[playerIndex][category] = score;

				updateTotalScore(playerIndex);
				display.updateScorecard(category, playerIndex, score);
				break;

			} else {
				display.printMessage("Select an unused category.");
			}

		}

	}

//	fill unfilled categories	
	private void addUnfilledScores() {
		addUpperScore();
		addBonusScore();
		addLowerScore();
		changeTotalScore();
		addUnfilledCategories();

	}

//	fill categories in which player has 0	
	private void addUnfilledCategories() {
		for (int i = 1; i <= nPlayers; i++)
			for (int j = ONES; j <= CHANCE; j++)
				if (j != UPPER_SCORE && j != UPPER_BONUS && points[i][j] == 0)
					display.updateScorecard(j, i, 0);

	}

//	change total score after choosing category
	private void changeTotalScore() {
		for (int i = 1; i <= nPlayers; i++)
			updateTotalScore(i);
	}

//	fill lower score 	
	private void addLowerScore() {
		for (int i = 1; i <= nPlayers; i++) {
			int point = 0;
			for (int j = THREE_OF_A_KIND; j <= CHANCE; j++)
				point += points[i][j];

			display.updateScorecard(LOWER_SCORE, i, point);
		}

	}

//	fill bonus score	
	private void addBonusScore() {
		for (int i = 1; i <= nPlayers; i++)
			display.updateScorecard(UPPER_BONUS, i, points[i][UPPER_BONUS]);

	}

//	fill upper score	
	private void addUpperScore() {
		for (int i = 1; i <= nPlayers; i++) {
			int point = 0;
			for (int j = ONES; j <= SIXES; j++)
				point += points[i][j];

			if (point >= 63)
				points[i][UPPER_BONUS] = 35;
			display.updateScorecard(UPPER_SCORE, i, point);
		}

	}

//	fill total score after game with bonus score	
	private void updateTotalScore(int playerIndex) {
		int point = 0;
		for (int i = ONES; i <= CHANCE; i++)
			if (i != UPPER_SCORE)
				point += points[playerIndex][i];

		points[playerIndex][TOTAL] = point;
		display.updateScorecard(TOTAL, playerIndex, point);

	}

//	check if dice belongs to category	
	private int getScore(int[] dice, int category) {
		int score = 0;

		if (category == ONES)
			score = checkForOnes(dice);
		if (category == TWOS)
			score = checkForTwos(dice);
		if (category == THREES)
			score = checkForThrees(dice);
		if (category == FOURS)
			score = checkForFours(dice);
		if (category == FIVES)
			score = checkForFives(dice);
		if (category == SIXES)
			score = checkForSixes(dice);
		if (category == THREE_OF_A_KIND)
			score = checkForThreeOfKind(dice);
		if (category == FOUR_OF_A_KIND)
			score = checkForFourOfKind(dice);
		if (category == FULL_HOUSE)
			score = checkForFullHouse(dice);
		if (category == SMALL_STRAIGHT)
			score = checkForSmallStraight(dice);
		if (category == LARGE_STRAIGHT)
			score = checkForLargeStraight(dice);
		if (category == YAHTZEE)
			score = checkForYahtzee(dice);
		if (category == CHANCE)
			score = checkForChance(dice);

		return score;
	}

//	check for chance	
	private int checkForChance(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			point += dice[i];

		return point;

	}

//	check for yahtzee	
	private int checkForYahtzee(int[] dice) {
		int n = dice[0];
		for (int i = 1; i < N_DICE; i++)
			if (dice[i] != n)
				return 0;

		return 50;

	}

//	check for large straight	
	private int checkForLargeStraight(int[] dice) {
		boolean[] t = new boolean[7];
		for (int i = 0; i < N_DICE; i++)
			t[dice[i]] = true;

		for (int i = 2; i <= 5; i++)
			if (!t[i])
				return 0;

		if (t[1] || t[6])
			return 40;

		return 0;

	}

//	check for small straight	
	private int checkForSmallStraight(int[] dice) {
		boolean[] t = new boolean[7];
		for (int i = 0; i < N_DICE; i++)
			t[dice[i]] = true;

		if (!t[3] || !t[4])
			return 0;

		if ((t[1] && t[2]) || (t[5] && t[6]) || (t[2] && t[5]))
			return 30;

		return 0;

	}

//	check for full house	
	private int checkForFullHouse(int[] dice) {
		int a = dice[0], b = 0;
		int n_a = 1;
		int n_b = 0;
		for (int i = 1; i < N_DICE; i++)
			if (dice[i] == a)
				n_a++;
			else {
				n_b++;
				b = dice[i];
			}

		for (int i = 0; i < N_DICE; i++)
			if (dice[i] != a && dice[i] != b)
				return 0;

		if (Math.abs(n_a - n_b) == 1)
			return 25;

		return 0;

	}

//	check for four of a kind	
	private int checkForFourOfKind(int[] dice) {
		int[] mas = new int[7];
		int point = 0;
		for (int i = 0; i < N_DICE; i++) {
			mas[dice[i]]++;
			point += dice[i];
		}

		for (int i = 1; i <= 6; i++)
			if (mas[i] >= 4)
				return point;

		return 0;

	}

//	check for three of a kind	
	private int checkForThreeOfKind(int[] dice) {
		int[] mas = new int[7];
		int point = 0;
		for (int i = 0; i < N_DICE; i++) {
			mas[dice[i]]++;
			point += dice[i];
		}

		for (int i = 1; i <= 6; i++)
			if (mas[i] >= 3)
				return point;

		return 0;

	}

//	check for sixes	
	private int checkForSixes(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			if (dice[i] == 6)
				point++;

		return 6 * point;

	}

//	check for fives	
	private int checkForFives(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			if (dice[i] == 5)
				point++;

		return 5 * point;

	}

//	check for fours	
	private int checkForFours(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			if (dice[i] == 4)
				point++;

		return 4 * point;

	}

//	check for threes	
	private int checkForThrees(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			if (dice[i] == 3)
				point++;

		return 3 * point;

	}

//	check for twos	
	private int checkForTwos(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			if (dice[i] == 2)
				point++;

		return 2 * point;

	}

//	check for ones	
	private int checkForOnes(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++)
			if (dice[i] == 1)
				point++;

		return point;

	}

	/* Private instance variables */
	private int nPlayers;
	int[][] points = new int[MAX_PLAYERS + 1][N_CATEGORIES + 1];
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
