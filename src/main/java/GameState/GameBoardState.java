package GameState;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * GameState class which holds a local game state represented as a 2d matrix.
 * the game state values.
 * 
 * @Note Expected Range of values 0 = empty 1 = white queen 2 = black queen 3 =
 *       arrow
 * 
 *       Note: size is not 10 because input also includes the labeling from 1-10
 *       and a-j for gui which is why BOARD_WIDTH and BOARD_HIEGHT are 11.
 * 
 * @param BOARD_WIDTH  set to 11
 * @param BOARD_HIEGHT set to 11
 * 
 * 
 *
 */
public class GameBoardState {

	public final static int BOARD_WIDTH = 11;
	public final static int BOARD_HIEGHT = 11;

	int[][] currentBoard = new int[BOARD_WIDTH][BOARD_HIEGHT];

	private  ArrayList<int[]> queenPose1 = new ArrayList<>();
	private  ArrayList<int[]> queenPose2 = new ArrayList<>();

	// static int[][] savedBoard = new int[BOARD_WIDTH][BOARD_HIEGHT];

	/**
	 * CONSTRUCTOR EFFECTS: currentBaord, creates 2d array representation of the
	 * game board. Modifies: currentBoard
	 * 
	 * @param gameBaord is list of integers collected from
	 *                  AmazonsGameMessage.GAME_STATE
	 */
	public GameBoardState(ArrayList<Integer> gameBoard) {
		System.out.println(gameBoard.toString());
		for (int y = 1; y < BOARD_WIDTH; y++) {
			for (int x = 1; x < BOARD_HIEGHT; x++) {
				currentBoard[y][x] = Integer.valueOf(gameBoard.get(y * 11 + x));

			}
		}

	}

	public GameBoardState(int[][] gameBoard) {
		this.currentBoard = gameBoard;
	}

	/**
	 * EFFECTS: sets new value of of board x position and y position MODIFIES:
	 * currentBaord
	 * 
	 * @param newValue the new value that will be set in the matrix
	 * @param x        is the x integer value position of a column 1-10
	 * @param y        is the y integer value position of a row 1-10
	 */
	public ArrayList<Integer> setPosValue(int newvalue, int x, int y) {
		if (x < 1 || y < 1 || x > 10 || y > 10)
			throw new IndexOutOfBoundsException("index must be between 1-11 inclusive");
		currentBoard[x][y] = newvalue;
		ArrayList<Integer> posVal = new ArrayList<Integer>((Arrays.asList(x,y)));
		return  posVal;
	}

	@Override
	public String toString() {
		String msg = "";
		for (int i = 1; i < BOARD_WIDTH; i++) {
			msg += "\n";
			for (int j = 1; j <BOARD_HIEGHT; j++) {
				msg += "  " + currentBoard[i][j];
			}
		}
		
		
		return msg;

	}

	/**
	 * print to console currentBoard as matrix prints the representation with the
	 * square 1a as top left corner. which makes the console representation flip'd
	 * on the x-axis in relation to the GUI.
	 */
	public void print() {
		System.out.println(toString());
	}

	public int[][] getCurBoard() {
		return currentBoard;
	}

	public void setCurBoard(int[][] board) {
		currentBoard = board;
	}

	/**
	 * EFFECTS: count the the number of queens on both sides expected their should
	 * always be four of each.
	 * 
	 * @return returns 2d array position 0 is white queen position 1 is black queen
	 */
	public int[] countQueens() {
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
		return new int[] { Queen1, Queen2 };
	}

	
	
	public void updatePoses() {
		ArrayList<int[]> q1w = new ArrayList<>(4);
		ArrayList<int[]> q2b = new ArrayList<>(4);

		for (int y = 1; y < BOARD_WIDTH; y++) {
			for (int x = 1; x < BOARD_WIDTH; x++) {
				if (currentBoard[y][x] == 1) {
					q1w.add(new int[] { y, x });
				}
				if (currentBoard[x][y] == 2) {
					q2b.add(new int[] { y, x });
				}

			}

		}
		queenPose1 = q1w;
		queenPose2 = q2b;
		
		

	}
	
	public void printQPoses() {
		for(int i = 0; i <4; i++)
			System.out.print(Arrays.toString(queenPose1.get(i)));
	}
	
	public ArrayList<int[]>getQueenPosition1() {
		return queenPose1 ; 
	}
	public ArrayList <int[]>getQueenPosition2() {
		return queenPose2 ; 
	}
}
