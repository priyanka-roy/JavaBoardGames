package com.mancala.java;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class GameBoard {
	private Map<Integer, Integer> boardPits;
	private static final int BOARD_LENGTH = 14;
	public static final String WINNER = "Winner";
	private int currentPlayer;
	private int counterScorePlayer1;
	private int counterScorePlayer2;
	private boolean terminate = false;

	public GameBoard() {
		this.boardPits = new LinkedHashMap<Integer, Integer>();
		for (int i = 0; i < BOARD_LENGTH; i++) {
			this.boardPits.put(i, 3);
		}
		boardPits.put(6, 0);
		boardPits.put(13, 0);
		this.currentPlayer = 0;
		this.terminate = false;

	}

	public void printGameBoard() {
		boardPits.forEach((k, v) -> {
			System.out.println("Pit NO: -> " + k + " Stones : " + v);

		});

	}

	public void moveAndPlay(int position) {

		int turn = boardPits.get(position);
		boardPits.put(position, 0);
		while (turn > 0) {
			position = nextPosition(position);
			int value = boardPits.get(position);
			if (currentPlayer == 1 && position == 6) {
				continue;
			} else if (currentPlayer == 2 && position == 13) {
				continue;
			} else {
				boardPits.put(position, ++value);
				turn--;
			}

		}
		printGameBoard();
		checkEmptyPits();
		if (counterScorePlayer1 == 6 || counterScorePlayer2 == 6) {
			calculateScore();
			stopGame();
		}
		if (position == 6) {
			System.out.println("player 1 gets an extra turn");
			currentPlayer = 1;
			askPlayerInput(currentPlayer);
		}
		if (position == 13) {
			System.out.println("player 2 gets an extra turn");
			currentPlayer = 2;
			askPlayerInput(currentPlayer);
		}
		askIfContinue();
		if(isTerminate()){
			stopGame();
		}
		askPlayerInput(currentPlayer);

	}

	public void stopGame() {
		if (isTerminate()) {
			System.exit(0);
		}

	}

	private void askPlayerInput(int currentPlayer) {
		if (currentPlayer == 1) {
			System.out.println("Player One :Active Now ");
			System.out.println("Player One :Please enter any number from 0 to 5  ");
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			int num1 = Integer.parseInt(input.nextLine());
			this.currentPlayer = 2;
			moveAndPlay(num1);
		} else {
			System.out.println("Player Two :Active Now ");
			System.out.println("Player Two :Please enter any number from 7 to 12  ");
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			int num1 = Integer.parseInt(input.nextLine());
			this.currentPlayer = 1;
			moveAndPlay(num1);
		}

	}

	private void askIfContinue() {
		System.out.println("Do you want to continue plaing.......type Y for yes and N for No");
		Scanner scanner = new Scanner(System.in);
		String answers = scanner.nextLine();
		if (answers.equalsIgnoreCase("y")) {
			setTerminate(false);
		} else {
			setTerminate(true);

		}

	}

	// move to next pit position in the board.
	private int nextPosition(int position) {
		if (++position >= BOARD_LENGTH)
			position = 0;
		return position;
	}

	// checkEmpty for empty pits.
	public void checkEmptyPits() {
		counterScorePlayer1 = 0;
		counterScorePlayer2 = 0;

		for (int i = 0; i < 6; i++) {
			if (boardPits.get(i) == 0) {
				++counterScorePlayer1;
			}
		}
		for (int i = 7; i < 13; i++) {
			if (boardPits.get(i) == 0) {
				++counterScorePlayer2;
			}

		}

	}

	// Calculate Score for Winner
	public void calculateScore() {
		if (boardPits.get(6) > boardPits.get(13)) {

			System.out.println("Player 1" + WINNER);
		} else {
			System.out.println("Player 2" + WINNER);

		}

		setTerminate(true);

	}

	/**
	 * Getters and Setters for Testing purposes
	 */

	public int getCounterScorePlayer1() {
		return counterScorePlayer1;
	}

	public void setCounterScorePlayer1(int counterScorePlayer1) {
		this.counterScorePlayer1 = counterScorePlayer1;
	}

	public Map<Integer, Integer> getBoardPits() {
		return boardPits;
	}

	public boolean isTerminate() {
		return terminate;
	}

	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}

}
