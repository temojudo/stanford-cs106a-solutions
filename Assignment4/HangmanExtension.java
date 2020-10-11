
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class HangmanExtension extends ConsoleProgram {

	private static final int NTURNS = 8;
	private static final double DELAY = 200;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	HangmanLexicon s = new HangmanLexicon();

	private HangmanCanvasExtension canvas;

	public void init() {
		canvas = new HangmanCanvasExtension();
		add(canvas);
	}

	public void run() {

		println("Welcome to Hangman!");

		while (true) {
			playGame();
			readLine("Enter any letter to start new game: ");
		}

	}

//	In this method you play game till you guess word or you miss NTURNS letters
	private void playGame() {

		canvas.reset();
		String word = s.getWord(rgen.nextInt(s.getWordCount()));
		String str = createStartingWord(word.length());
		canvas.displayWord(str);
		printStr(str);

		char ch;
		int nturns = NTURNS;

		while (nturns > 0) {

			guessesLeft(nturns);
			ch = enterChar();

			if (isPresent(ch, word)) {

				println("That guess is correct.");
				str = ChangeWord(ch, word, str);
				canvas.displayWord(str);

				if (!isWordOpened(str, word)) {
					printStr(str);
				}

			} else {

				canvas.noteIncorrectGuess(ch);
				nturns--;
				println("There are no " + ch + "'s in the word.");

				if (nturns > 0) {
					printStr(str);
				}

			}

			if (isWordOpened(str, word)) {

				println("You guessed the word: " + word);
				println("You win.");

				pause(DELAY);
				canvas.winningMessage(str);
				break;
			}

		}

		if (!isWordOpened(str, word)) {

			println("You're completely hung.");
			println("The word was: " + word);
			println("You lose.");

			pause(DELAY);
			canvas.losingMessage(str);

		}

	}

// 	Enter one symbol string only "a"-"z" or "A"-"Z"
	private char enterChar() {
		char ch;

		while (true) {
			String l = readLine("Your guess: ");
			if (l.length() != 1) {
				println("You've entered wrong letter.");
				continue;
			}
			ch = l.charAt(0);
			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
				break;
			}
			println("You've entered wrong letter.");
		}

		return ch;
	}

// 	Check if the word is guessed
	private boolean isWordOpened(String str, String word) {
		if (str.compareTo(word) == 0) {
			return true;
		}
		return false;
	}

// 	Print guessed letters and dashes	
	private void printStr(String str) {
		print("The word now looks like this:");
		for (int i = 0; i < str.length(); i++) {
			print(" " + str.charAt(i));
		}
		println();

	}

//	Change str with  new guessed letter	
	private String ChangeWord(char ch, String word, String str) {
		for (int i = 0; i < word.length(); i++) {
			if (ch == word.charAt(i) || ch == word.charAt(i) + 'a' - 'A') {
				str = str.substring(0, i) + ch + str.substring(i + 1);
			}
		}
		str = str.toUpperCase();
		return str;
	}

// 	Create only dashes	
	private String createStartingWord(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str += "_";
		}
		return str;
	}

//	Find out if the letter is in the word	
	private boolean isPresent(char ch, String word) {
		for (int i = 0; i < word.length(); i++) {
			if (ch == word.charAt(i) || ch == word.charAt(i) + 'a' - 'A') {
				return true;
			}
		}
		return false;
	}

//	Print how many guesses is left in the game	
	private void guessesLeft(int i) {
		if (i == 1) {
			println("You have only one guess left.");
		} else {
			println("You have " + i + " guesses left.");
		}

	}

}
