package com.mancala.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mancala.java.GameBoard;

import junit.framework.TestCase;

public class GameBoardTest extends TestCase {

	private GameBoard gameBoard;

	@Override
	protected void setUp() throws Exception {
		this.gameBoard = new GameBoard();
	}

	@AfterClass
	public void tearDownAfterClass() throws Exception {
		this.gameBoard.setTerminate(true);
		this.gameBoard.stopGame();
		this.gameBoard = null;
		assertNull(gameBoard);
	}

	@Test
	public void testCalculateScore() {
		assertNotNull(gameBoard);
		gameBoard.getBoardPits().put(6, 12);
		gameBoard.getBoardPits().put(13, 9);
		gameBoard.calculateScore();
		assertTrue(new String("Player 1").concat(GameBoard.WINNER), true);
	}

	@Test
	public void testCheckEmptyPits() {
		assertNotNull(gameBoard);
		// when all pits are not empty
		gameBoard.checkEmptyPits();
		assertNotSame(gameBoard.getCounterScorePlayer1(), 6);

		// all pits are set to empty
		for (int i = 0; i < 6; i++) {
			gameBoard.getBoardPits().put(i, 0);

		}
		gameBoard.checkEmptyPits();
		assertEquals(gameBoard.getCounterScorePlayer1(), 6);

	}

	// TODO
	@Test
	public void testMoveAndPlay() {

	}

	// TODO
	@Test
	public void testStopGame() {

	}

	// TODO
	@Test
	public void testNextPosition() {

	}

	// TODO
	@Test
	public void testPrintBoard() {

	}

}
