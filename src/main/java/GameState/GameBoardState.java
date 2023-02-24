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

	private ArrayList<int[]> queenPose1White = new ArrayList<>();
	private ArrayList<int[]> queenPose2Black = new ArrayList<>();

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
				currentBoard[BOARD_WIDTH - y][x] = Integer.valueOf(gameBoard.get(y * 11 + x));

			}
		}

	}

	public GameBoardState(int[][] gameBoard) {
		this.currentBoard = gameBoard;
	}

	// ------Helper Methods-----------

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

	public void printQPoses() {
		for (int i = 0; i < queenPose1White.size(); i++) {
			System.out.print(Arrays.toString(queenPose1White.get(i)));

		}
		System.out.println();
		for (int i = 0; i < queenPose2Black.size(); i++) {
			System.out.print(Arrays.toString(queenPose2Black.get(i)));

		}
		System.out.println();
	}

	public ArrayList<int[]> getQueenPosition1() {
		return queenPose1White;
	}

	public ArrayList<int[]> getQueenPosition2() {
		return queenPose2Black;
	}

	@Override
	public String toString() {
		String msg = "";
		for (int y = 1; y < BOARD_WIDTH; y++) {
			msg += "\n";
			for (int x = 1; x < BOARD_HIEGHT; x++) {
				msg += "  " + currentBoard[BOARD_WIDTH - y][x];
			}
		}

		return msg;

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
		ArrayList<Integer> posVal = new ArrayList<Integer>((Arrays.asList(x, y)));
		return posVal;
	}

	/**
	 * EFFECTS: Moves a single Queen and shoots a arrow. MODIFIES: QueenPose1White
	 * or QueenPOse2Black, CurrentBoard
	 * 
	 * @param queenColor  the queen color to select 1 is white 2 is black
	 * @param oldPosXY    is the current queens position as a (x,y) pair
	 * @param newPosXY    is the new desired position of the queen as a (x,y) pair
	 * @param newArrowPos is the desired Arrow position as a (x,y) pair.
	 * 
	 * @return a Array-list object that contains that data to send to server index 0
	 *         = current queen position index 1 = new queen Position index 3 = new
	 *         arrow position
	 */
	public ArrayList<ArrayList<Integer>> MoveQueen(int queenColor, int[] oldPosXY, int[] newPosXY, int[] newArrowPos) {
		// if out of bounds throw exception
		if (currentBoard[oldPosXY[0]][oldPosXY[1]] != queenColor) {
			throw new IndexOutOfBoundsException("that is not a vaible queen to move. queen: " + queenColor
					+ " :: pos: [" + oldPosXY[0] + "," + oldPosXY[1] + "]");
		}
		if (newPosXY[0] < 1 || newPosXY[1] < 1 || newPosXY[0] > 10 || newPosXY[1] > 10)
			throw new IndexOutOfBoundsException("index must be between 1-11 inclusive");

		// retrieve index of queen that moved
		int queenIndex = 0;
		if (queenColor == 1) {
			for (int i = 0; i < 4; i++)
				if (queenPose1White.get(i)[0] == oldPosXY[0] && queenPose1White.get(i)[1] == oldPosXY[1]) {
					queenIndex = i;
					break;
				}
		}

		if (queenColor == 2) {
			for (int i = 0; i < 4; i++)
				if (queenPose2Black.get(i)[0] == oldPosXY[0] && queenPose2Black.get(i)[1] == oldPosXY[1]) {
					queenIndex = i;
					break;
				}
		}
		// package data into server readable's with new move
		ArrayList<ArrayList<Integer>> senderObj = new ArrayList<>();
		senderObj.add(this.setPosValue(0, oldPosXY[0], oldPosXY[1]));
		senderObj.add(this.setPosValue(queenColor, newPosXY[0], newPosXY[1]));
		senderObj.add(this.setPosValue(3, newArrowPos[0], newArrowPos[1]));

		// update queen that moved locally
		if (queenColor == 1) {
			queenPose1White.get(queenIndex)[0] = newPosXY[0];
			queenPose1White.get(queenIndex)[1] = newPosXY[1];
		}
		if (queenColor == 2) {
			queenPose2Black.get(queenIndex)[0] = newPosXY[0];
			queenPose2Black.get(queenIndex)[1] = newPosXY[1];
		}
		// return sender object data
		return senderObj;

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

	/**
	 * EFFECTS: updates location of queens locally by searching for them. MODIFIES:
	 * queenPoses1White and queenPoses2Black
	 */
	public void updateQueenPoses() {
		ArrayList<int[]> q1w = new ArrayList<>(4);
		ArrayList<int[]> q2b = new ArrayList<>(4);

		for (int y = 1; y < BOARD_WIDTH; y++) {
			for (int x = 1; x < BOARD_WIDTH; x++) {
				if (currentBoard[y][x] == 1) {
					q1w.add(new int[] { y, x });
				}
				if (currentBoard[y][x] == 2) {
					q2b.add(new int[] { y, x });
				}

			}

		}
		queenPose1White = q1w;
		queenPose2Black = q2b;

	}

}
