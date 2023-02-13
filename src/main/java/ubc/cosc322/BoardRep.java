package ubc.cosc322;
import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.GameMessage;
public class BoardRep {

	
	final static int  boardWidth = 11;
	final static int  boardHeight = 11;
	
	 static int[][] board = new int[boardWidth][boardHeight];
	
	public BoardRep(ArrayList<Integer> gameBoard) {
		for(int i= 0; i < boardWidth; i++) {
			for(int j = 0; j <boardHeight; j++) {
				board[i][j] = Integer.valueOf(gameBoard.get(i*10+j));
			}
		}
		
	}
	public String toString() {
		String msg ="";
		for(int i =0; i<boardWidth;i++) {
			msg += "\n";
			for(int j =0; j <boardHeight; j++) {
				msg += " , "+board[i][j];
			}
		}
		return msg;
		
				
	}
	public static void countQueens() {
		int Queen1 = 0;
		int Queen2 = 0;
		for(int i= 0; i < boardWidth; i++) {
			for(int j = 0; j <boardHeight; j++) {
				if(board[i][j] == 1)
					Queen1++;
				if(board[i][j] == 2)
					Queen2++;
			}
		}
		System.out.println("q1 = "+Queen1 +" q2 = "+ Queen2);
	}
	
}
