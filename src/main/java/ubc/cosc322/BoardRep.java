package ubc.cosc322;
import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.GameMessage;
public class BoardRep {

	
	final static int  boardWidth = 10;
	final static int  boardHeight = 10;
	
	 int[][] board = new int[boardWidth][boardHeight];
	
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
	
	public static void main(String[] args) {
		ArrayList<Integer> testbaord = new ArrayList<>();
		for(int i= 0; i < boardWidth; i++) {
			for(int j = 0; j <boardHeight; j++) {
				testbaord.add(0);
			}
		}
		BoardRep test = new BoardRep(testbaord);
		System.out.println(test.toString());
	}
	
}
