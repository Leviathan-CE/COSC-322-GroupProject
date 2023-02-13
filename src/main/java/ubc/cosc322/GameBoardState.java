package ubc.cosc322;
import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.GameMessage;
public class GameBoardState {

	
	final static int  boardWidth = 11;
	final static int  boardHeight = 11;
	
	 static int[][] currentBoard = new int[boardWidth][boardHeight];
	 static int[][] savedBoard = new int[boardWidth][boardHeight];
	
	 /*
	  * EFFECTS: currentBaord, creates 2d array representation of the game board.
	  * Modifies: currentBoard
	  */
	public GameBoardState(ArrayList<Integer> gameBoard) {
		for(int i= 1; i < boardWidth; i++) {
			for(int j = 1; j <boardHeight; j++) {
				currentBoard[i][j] = Integer.valueOf(gameBoard.get(i*11+j));				
			}
		}
		
	}
	public String toString() {
		String msg ="";
		for(int i =1; i<boardWidth;i++) {
			msg += "\n";
			for(int j =1; j <boardHeight; j++) {
				msg += " , "+currentBoard[i][j];
			}
		}
		return msg;
		
				
	}
	/*
	 * EFFECTS: takes the current
	 */
	public void SaveBoard() {
		for(int i= 0; i < boardWidth; i++) {
			for(int j = 0; j <boardHeight; j++) {
				savedBoard[i][j] = currentBoard[i][j];
			}
		}
	}
	
	public static void countQueens() {
		int Queen1 = 0;
		int Queen2 = 0;
		for(int i= 0; i < boardWidth; i++) {
			for(int j = 0; j <boardHeight; j++) {
				if(currentBoard[i][j] == 1)
					Queen1++;
				if(currentBoard[i][j] == 2)
					Queen2++;
			}
		}
		System.out.println("q1 = "+Queen1 +" q2 = "+ Queen2);
	}
	
}
