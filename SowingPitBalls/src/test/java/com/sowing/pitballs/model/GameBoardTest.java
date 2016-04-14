package com.sowing.pitballs.model;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class GameBoardTest extends TestCase {

	private GameBoard gameBoard;

	@Before
	public void setUp() throws Exception {
		this.gameBoard = new GameBoard();
		assertNotNull(gameBoard);

	}

	@After
	public void tearDown() throws Exception {
		this.gameBoard = null;
		assertNull(gameBoard);
	}

	@Test
	public void testCalculateScore() {
		assertNotNull(gameBoard.getBoardPits());
		gameBoard.getBoardPits().get(gameBoard.PLAYER_0NE_BIG_PIT).setStones(9);
		gameBoard.getBoardPits().get(gameBoard.PLAYER_TWO_BIG_PIT).setStones(5);

		if (!gameBoard.checkEmptyPits()) {
			for(int i = 0;i<6;i++){
				gameBoard.getBoardPits().get(i).setStones(0);
			}
			gameBoard.calculateScore();
			// player 1 is winner
			assertEquals(1, gameBoard.getWinner());
		}

	}

	@Test
	public void testCheckEmptyPits() {
		assertNotNull(gameBoard);
		// when all pits are not empty
		assertFalse(gameBoard.checkEmptyPits());
		// for player 1 set all pits  to empty
		for(int i = 0;i<6;i++){
			gameBoard.getBoardPits().get(i).setStones(0);
		}
		
		assertTrue(gameBoard.checkEmptyPits());

	}

	
	@Test
	public void testCheckNextPlayer() {
		assertNotNull(gameBoard);
		//for player 1
		int currentplayer = 1;
		gameBoard.checkNextPlayer(currentplayer, 0);
		assertTrue(gameBoard.getNextPlayer()== 2 );
		//extra turn for player 1
		gameBoard.checkNextPlayer(currentplayer, gameBoard.PLAYER_0NE_BIG_PIT);
		assertTrue(gameBoard.getNextPlayer()== 1 );
		//for player 2
		int currentPlayer = 2;
		gameBoard.checkNextPlayer(currentPlayer, 8);
		assertTrue(gameBoard.getNextPlayer()== 1 );
		//extra turn for player 2
		gameBoard.checkNextPlayer(currentPlayer, gameBoard.PLAYER_TWO_BIG_PIT);
		assertTrue(gameBoard.getNextPlayer()== 2 );
		
	}

	
	@Test
	public void testMoveAndPlay() {
		assertNotNull(gameBoard);
		int player = 2;
		int position = 7;
		List<Pit> originalList = gameBoard.getBoardPits();
		gameBoard.moveAndPlay(player, position);
		//emtpy the present pit logic
		assertTrue(originalList.get(7).getStones()== 0);
		//cannot throw in the opponents's pit when current player is 2
		int opponetPit = gameBoard.PLAYER_0NE_BIG_PIT;
		int stonesAtOpponetPitBeforePlay = gameBoard.getBoardPits().get(gameBoard.PLAYER_0NE_BIG_PIT).getStones();
		gameBoard.moveAndPlay(player, opponetPit);
		int stonesAtOpponetPitAfterPlay = gameBoard.getBoardPits().get(gameBoard.PLAYER_0NE_BIG_PIT).getStones();
		//stones at opponent's pit remains unchanged
		assertTrue(stonesAtOpponetPitBeforePlay == stonesAtOpponetPitAfterPlay);
		
		
		

	}

}