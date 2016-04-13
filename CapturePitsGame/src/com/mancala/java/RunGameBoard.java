package com.mancala.java;

import java.util.Scanner;

public class RunGameBoard {

	public static void main(String[] args) {
		GameBoard board = new GameBoard();
		board.printGameBoard();
		
		
		System.out.println("Please enter any number from 0 to 5  " );
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		int num1 = Integer.parseInt(input.nextLine());
		
		board.moveAndPlay(num1);
	
		
		
	}

}
