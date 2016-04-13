package com.sowing.pitballs.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GameBoard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -244769085540964293L;
	private List<Pit> boardPits;
	public static final int BOARD_LENGTH = 14;
	public static final int PLAYER_0NE_BIG_PIT = 6;
	public static final int PLAYER_TWO_BIG_PIT = 13;
	public static final int STONES_IN_PIT = 6;
	private int nextPlayer;
	private int pitNo;
	private int counterScorePlayer1 = 0;
	private int counterScorePlayer2 =0;
	private int winner;
	private boolean terminate;

	public GameBoard() {
		boardPits = new LinkedList<Pit>();
		for (int i = 0; i < 14; i++) {
			boardPits.add(new Pit(i, STONES_IN_PIT));

		}
		Pit pitBigPiA = boardPits.get(PLAYER_0NE_BIG_PIT);
		pitBigPiA.setStones(0);
		Pit pitBigPiB = boardPits.get(PLAYER_TWO_BIG_PIT);
		pitBigPiB.setStones(0);
		terminate = false;

	}

	

	public void moveAndPlay(int currentPlayer, int position) {

		int turn = boardPits.get(position).getStones();
		Pit emptyPit = boardPits.get(position);
		emptyPit.setStones(0);

		while (turn > 0) {
			position = nextPosition(position);
			int value = boardPits.get(position).getStones();
			if (currentPlayer == 1 && position == PLAYER_TWO_BIG_PIT) {
				continue;
			} else if (currentPlayer == 2 && position == PLAYER_0NE_BIG_PIT) {
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

	public void checkNextPlayer(int currentPlayer, int position) {
		if (position == PLAYER_0NE_BIG_PIT) {
			setNextPlayer(1);
		} else if (position == PLAYER_TWO_BIG_PIT) {
			setNextPlayer(2);
		} else if (currentPlayer == 1) {
			setNextPlayer(2);
		} else {
			setNextPlayer(1);

		}
	}

	// checkEmpty for empty pits.
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

	
	// Calculate Score for Winner
	public void calculateScore() {

	if (checkEmptyPits()) {
			if (boardPits.get(PLAYER_0NE_BIG_PIT).getStones() > boardPits.get(PLAYER_TWO_BIG_PIT).getStones()) {
				setWinner(1);
			}else{
			setWinner(2);
			}
		
			
	}
			
		

		

	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public void stopGame() {
		if(isTerminate()){
			System.exit(0);
		}
		

	}

	// move to next pit position in the board.
	private int nextPosition(int position) {
		if (++position >= BOARD_LENGTH)
			position = 0;
		return position;
	}

	////////////////////////////////////////////////////////////////////
	public List<Pit> getBoardPits() {
		return boardPits;
	}

	public void setBoardPits(List<Pit> boardPits) {
		this.boardPits = boardPits;
	}

	public int getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(int nextPlayer) {
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



	

}
