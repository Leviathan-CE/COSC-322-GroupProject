package GameState;

import java.util.ArrayList;
import java.util.Map;

import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
/**
 * GameState class which holds a local game state represented as a 2d matrix.
 * the game state values. 
 * @Note Expected Range of values
 * 0 = empty 
 * 1 = white queen
 * 2 = black queen
 * 3 = arrow
 * 
 * Note: size is not 10 because input also includes the labeling from 1-10 and a-j for gui
 * which is why BOARD_WIDTH and BOARD_HIEGHT are 11.
 * 
 * @param BOARD_WIDTH set to 11 
 * @param BOARD_HIEGHT set to 11
 * 
 * 
 *
 */
public class GameBoardState {

	public final static int BOARD_WIDTH = 11;
	public final static int BOARD_HIEGHT = 11;

	 static int[][] currentBoard = new int[BOARD_WIDTH][BOARD_HIEGHT];
	 //public int[][] copyCurrentBoard = currentBoard;
	 //static int[][] savedBoard = new int[BOARD_WIDTH][BOARD_HIEGHT];

	/**
	 * CONSTRUCTOR EFFECTS: currentBaord, creates 2d array representation of the
	 * game board. Modifies: currentBoard
	 * @param gameBaord is list of integers collected from AmazonsGameMessage.GAME_STATE 
	 */
	public GameBoardState(ArrayList<Integer> gameBoard) {
		System.out.println(gameBoard.toString());
		for (int x = 1; x < BOARD_WIDTH; x++) {
			for (int y = 1; y < BOARD_HIEGHT; y++) {
				currentBoard[x][y] =Integer.valueOf(gameBoard.get(x * 11 + y));
			
			}
		}
		for(int[] val : currentBoard) {
			
		}

	}

	/**
	 * EFFECTS: sets new value of of board x position and y position 
	 * MODIFIES: currentBaord
	 * 
	 * @param newValue the new value that will be set in the matrix
	 * @param x        is the x integer value position of a column 1-10
	 * @param y        is the y integer value position of a row 1-10
	 */
	public void setPosValue(int newvalue, int x, int y) {
		if (x < 1 || y < 1 || x > 10 || y > 10)
			throw new IndexOutOfBoundsException("index must be between 1-11 inclusive");
		currentBoard[x][y] = newvalue;
	}

	@Override
	public String toString() {
		String msg = "";
		for (int i = 1; i < BOARD_WIDTH; i++) {
			msg += "\n";
			for (int j = 1; j < BOARD_HIEGHT; j++) {
				msg += "  " + currentBoard[i][j];
			}
		}
		return msg;

	}
	/**
	 *  print to console currentBoard as matrix
	 *  prints the representation with the square 1a 
	 *  as top left corner. which makes the console representation
	 *  flip'd on the x-axis in relation to the GUI.  
	 */
	public void print() {
		System.out.println(toString());
	}

	/*
	 * EFFECTS: takes the current board and deep copies it MODIFIES: savedBoard
	 */
	public void SaveBoard() {
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HIEGHT; j++) {
				//savedBoard[i][j] = currentBoard[i][j];
			}
		}
	}
	/**
	 * EFFECTS: count the the number of queens on both sides 
	 * expected their should always be four of each.
	 * @return returns 2d array position 0 is white queen position 1 is black queen
	 */
	public static int[] countQueens() {
		int Queen1 = 0;
		int Queen2 = 0;
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HIEGHT; j++) {
				if (currentBoard[i][j] == 1)
					Queen1++;
				if (currentBoard[i][j] == 2)
					Queen2++;
			}
		}
		System.out.println("q1 = " + Queen1 + " q2 = " + Queen2);
		return new int[] {Queen1,Queen2};
	}
	
	public boolean getIfMoveIsValid (int qx1, int qy1, int qx2, int qy2, int ax, int ay) {
		if(ifMoveIsValid(qx1, qy1, qx2, qy2)) {	//check  if it is valid to move from (qx1,qy1) to (qx2, qy2)
			if(ifMoveIsValid(qx2, qy2, ax, ay)) {	//then check if it is valid to shot arrow from (qx2, qy2) to (ax, ay)
				return true;
			}
		}
		
		return false;
	}

	public boolean ifMoveIsValid (int qx1, int qy1, int qx2, int qy2) {
		boolean isValid = false;
		int[][] board = currentBoard;
		if ((qy1 == qy2) && (qx1 == qx2)) {	//when it is located at the same coordinate.
			return isValid;
		}
		
		if( qy1 == qy2) {	//when coordinates of y is same, check vertically
			int start = qx1 < qx2? qx1: qx2;
			int end = qx1 < qx2? qx2: qx1;
			for(int i = start + 1; i <= end; i++) { //this for loop checks the vertical path of the queen from start to end and checks to make sure that path is clear
				if(board[i][qy1] !=0 ) return isValid;
			}
			isValid = true;
			return isValid;
		}
		
		if (qx1 == qx2) {	//when coordinates of x is same, check horizontally
			int start = qy1 < qy2? qy1: qy2;
			int end = qy1 < qy2? qy2: qy1;
			for(int i = start + 1; i <= end; i++) { //this for loop checks the horizontal path of the queen from start to end and checks to make sure that path is clear
				if(board[qx1][i] !=0 ) return isValid;
			}
			isValid = true;
			return isValid;
		}
		
		
		//diagonal checks
		if(Math.abs(qx2 - qx1) == Math.abs(qy2 - qy1)) {	//when coordinates on the y= 1*x + c, check diagonally
			if((qx1 < qx2) && (qy1 < qy2)) {
				for(int i = qx1 + 1, j = qy1 + 1; i <= qx2 && j <=qy2; i++, j++) {	//q1 to q2(from left to right, bottom to top)
					if(board[i][j] !=0 ) return isValid;
				}
			}
			else if ((qx1 < qx2) && (qy1 > qy2)) {
				for(int i = qx1 + 1, j = qy1 - 1; i <= qx2 && j <=qy2; i++, j--) {	//q1 to q2(from left to right, top to bottom)
					if(board[i][j] !=0 ) return isValid;
				}
				
			}
			else if ((qx1 > qx2) && (qy1 < qy2)) {
				for(int i = qx2 + 1, j = qy2 + 1; i <= qx1 && j <=qy1; i++, j++) {	//q2 to q1(from left to right, bottom to top)
					if(board[i][j] !=0 ) return isValid;
				}
				
			}
			else if ((qx1 > qx2) && (qy1 > qy2)) {
				for(int i = qx2 + 1, j = qy2 - 1; i <= qx1 && j <=qy1; i++, j--) {	//q2 to q1(from left to right, top to bottom)
					if(board[i][j] !=0 ) return isValid;
				}
				
			}
			isValid = true;
			return isValid;
		}
		
		return isValid;
	}

}


