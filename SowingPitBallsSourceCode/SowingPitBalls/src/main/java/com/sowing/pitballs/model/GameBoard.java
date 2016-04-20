package com.sowing.pitballs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameBoard implements Serializable {

	/**
	 * Represents the Game Board which is responsible to execute the logic for
	 * playing.
	 */
	private static final long serialVersionUID = -244769085540964293L;
	private List<Pit> boardPits;
	public static final int BOARD_LENGTH = 14;
	public static final int PLAYER_0NE_BIG_PIT = 6;
	public static final int PLAYER_TWO_BIG_PIT = 13;
	public static final int STONES_IN_PIT = 6;
	private Player nextPlayer;
	private int pitNo;
	private int counterScorePlayer1 = 0;
	private int counterScorePlayer2 = 0;
	private int winner;
	private boolean terminate;
	private Player[] players = new Player[2];
	
		final static Logger logger = LogManager.getLogger(GameBoard.class);

	public GameBoard() {
		boardPits = new ArrayList<Pit>();
		for (int i = 0; i < 14; i++) {
			boardPits.add(new Pit(i, STONES_IN_PIT));

		}
		Pit pitBigPiA = boardPits.get(PLAYER_0NE_BIG_PIT);
		pitBigPiA.setStones(0);
		Pit pitBigPiB = boardPits.get(PLAYER_TWO_BIG_PIT);
		pitBigPiB.setStones(0);
		terminate = false;
		players[0] = new Player(1, "Bob");
		players[1]= new Player(2, "Sam");
	

	}

	/**
	 * Responsible for moving to the next pit depending on the number of stones
	 * that was chosen by the Player. Also responsible for determining who the
	 * next player would be.
	 * 
	 * @param currentPlayer
	 * @param position
	 */
	public void moveAndPlay(Player currentPlayer, int position) {

		int turn = boardPits.get(position).getStones();
		Pit emptyPit = boardPits.get(position);
		emptyPit.setStones(0);

		while (turn > 0) {
			position = nextPosition(position);
			int value = boardPits.get(position).getStones();
			if (currentPlayer.getPlayerNo() == 1 && position == PLAYER_TWO_BIG_PIT) {

				continue;
			} else if (currentPlayer.getPlayerNo() == 2 && position == PLAYER_0NE_BIG_PIT) {

				continue;
			} else {
				Pit pit = boardPits.get(position);
				pit.setStones(++value);

				turn--;
			}

		}

		// updates pit values
		setBoardPits(boardPits);
		// Checks who is next player
		checkNextPlayer(currentPlayer, position);

	}

	/**
	 * Depending upon the previous player and his move determines who the next
	 * player would be
	 * 
	 * @param currentPlayer
	 * @param position
	 */
	public void checkNextPlayer(Player currentPlayer, int position) {
		if (position == PLAYER_0NE_BIG_PIT) {
			setNextPlayer(players[0]);
		} else if (position == PLAYER_TWO_BIG_PIT) {
			setNextPlayer(players[1]);
		} else if (currentPlayer.getPlayerNo() == 1) {
			setNextPlayer(players[1]);
		} else {
			setNextPlayer(players[0]);

		}
	}

	

	/**
	 * Checks for empty pits and then increments the counter for each player
	 * which is later needed to calculate the score If the counter for each
	 * player reaches 6,it returns true since it means that all the pits
	 * contains 0 stones in it.
	 * 
	 * @return
	 */
	public boolean checkEmptyPits() {
		counterScorePlayer1 = 0;
		counterScorePlayer2 = 0;

		for (int i = 0; i < PLAYER_0NE_BIG_PIT; i++) {
			if (boardPits.get(i).getStones() == 0) {
				++counterScorePlayer1;
			}
		}
		for (int i = 7; i < PLAYER_TWO_BIG_PIT; i++) {
			if (boardPits.get(i).getStones() == 0) {
				++counterScorePlayer2;
			}

		}

		if (counterScorePlayer1 == 6 || counterScorePlayer2 == 6) {
			return true;
		}
		return false;
	}

	/**
	 * Calculate Score for Winner Score is calculated by first checking for the
	 * empty pits and then the stones in each players Big Pit
	 */
	public void calculateScore() {

		if (checkEmptyPits()) {
			if (boardPits.get(PLAYER_0NE_BIG_PIT).getStones() > boardPits.get(PLAYER_TWO_BIG_PIT).getStones()) {
				setWinner(1);
			} else {
				setWinner(2);
			}

		}

	}

	
/**
 * Its better to use Thread to stop the Game 
 * Its a demo version ,so will implement it in the  later version 
 * 
 */
	public void stopGame() {
		if (isTerminate()) {
			System.exit(0);
		}

	}

	// move to next pit position in the board.
	private int nextPosition(int position) {
		if (++position >= BOARD_LENGTH)
			position = 0;
		return position;
	}

	/**
	 * All Getters and Setters go below
	 */
	
	public List<Pit> getBoardPits() {
		return boardPits;
	}

	public void setBoardPits(List<Pit> boardPits) {
		this.boardPits = boardPits;
	}

	

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public int getPitNo() {
		return pitNo;
	}

	public void setPitNo(int pitNo) {
		this.pitNo = pitNo;
	}

	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	public boolean isTerminate() {
		return terminate;
	}

	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}

	public int getCounterScorePlayer1() {
		return counterScorePlayer1;
	}

	public void setCounterScorePlayer1(int counterScorePlayer1) {
		this.counterScorePlayer1 = counterScorePlayer1;
	}

	public int getCounterScorePlayer2() {
		return counterScorePlayer2;
	}

	public void setCounterScorePlayer2(int counterScorePlayer2) {
		this.counterScorePlayer2 = counterScorePlayer2;
	}
	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

}
