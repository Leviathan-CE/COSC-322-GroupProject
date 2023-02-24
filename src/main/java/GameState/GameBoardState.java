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

	 public static int[][] currentBoard = new int[BOARD_WIDTH][BOARD_HIEGHT];
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

}
