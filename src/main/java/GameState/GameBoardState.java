package GameState;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import ActionFactory.Node;
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
public class GameBoardState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1004773758902058794L;
	public final static int BOARD_WIDTH = 11;
	public final static int BOARD_HIEGHT = 11;

	private String ID;
	private int hashCode = -1;
	int[][] currentBoard = new int[BOARD_WIDTH][BOARD_HIEGHT];

	private ArrayList<int[]> queenPose1White = new ArrayList<>();
	private ArrayList<int[]> queenPose2Black = new ArrayList<>();
	private ArrayList<int[]> arrowsPos = new ArrayList<>();
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
				ID = ID + String.valueOf(Integer.valueOf(gameBoard.get(y * 11 + x)));
			}
		}

	}

	public GameBoardState(int[][] gameBoard) {
		this.currentBoard = gameBoard;
	}

	// ------Helper Methods-----------

	public boolean equals(Node node) {
		if (this.ID.contentEquals(node.getID()))
			return true;
		return false;
	}

	/**
	 * EFFECTS: sums the values in each row by the rows index  and then 
	 * sums the column by the column index
	 * {X1j +X2j + xnj} * Xj 
	 */
	@Override
	public int hashCode() {
		int total = 0;
		if (hashCode == -1) {
			for (int y = 1; y < BOARD_WIDTH; y++) {
				for (int x = 1; x < BOARD_HIEGHT; x++) {
					total += y*x;
					total += x*y;
				}

			}
			hashCode = total;
			return total;
		}
		return hashCode;
	}
	public String getID() {return ID;}
	
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
	public ArrayList<int[]> getArrowPositions(){
		return arrowsPos;
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
		senderObj.add(this.setPosValue(0, oldPosXY[0], oldPosXY[1])); // set old move empty
		senderObj.add(this.setPosValue(queenColor, newPosXY[0], newPosXY[1])); // move queen to new location
		senderObj.add(this.setPosValue(3, newArrowPos[0], newArrowPos[1])); // place arrow

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
		ArrayList<int[]> arrw = new ArrayList<>();
		for (int y = 1; y < BOARD_WIDTH; y++) {
			for (int x = 1; x < BOARD_WIDTH; x++) {
				if (currentBoard[y][x] == 1) {
					q1w.add(new int[] { y, x });
				}
				if (currentBoard[y][x] == 2) {
					q2b.add(new int[] { y, x });
				}
				if(currentBoard[y][x] == 3) {
					arrw.add(new int[] {y,x});
				}

			}

		}
		queenPose1White = q1w;
		queenPose2Black = q2b;
		arrowsPos = arrw;
	}

	public boolean getIfMoveIsValid(int qx1, int qy1, int qx2, int qy2, int ax, int ay) {
		if (ifMoveIsValid(qx1, qy1, qx2, qy2)) { // check if it is valid to move from (qx1,qy1) to (qx2, qy2)
			if (ifMoveIsValid(qx2, qy2, ax, ay)) { // then check if it is valid to shot arrow from (qx2, qy2) to (ax,
													// ay)
				return true;
			}
		}

		return false;
	}

	public boolean ifMoveIsValid(int qx1, int qy1, int qx2, int qy2) {
		boolean isValid = false;
		int[][] board = currentBoard;
		if ((qy1 == qy2) && (qx1 == qx2)) { // when it is located at the same coordinate.
			return isValid;
		}

		if (qy1 == qy2) { // when coordinates of y is same, check vertically
			int start = qx1 < qx2 ? qx1 : qx2;
			int end = qx1 < qx2 ? qx2 : qx1;
			for (int i = start + 1; i <= end; i++) { // this for loop checks the vertical path of the queen from start
														// to end and checks to make sure that path is clear
				if (board[i][qy1] != 0)
					return isValid;
			}
			isValid = true;
			return isValid;
		}

		if (qx1 == qx2) { // when coordinates of x is same, check horizontally
			int start = qy1 < qy2 ? qy1 : qy2;
			int end = qy1 < qy2 ? qy2 : qy1;
			for (int i = start + 1; i <= end; i++) { // this for loop checks the horizontal path of the queen from start
														// to end and checks to make sure that path is clear
				if (board[qx1][i] != 0)
					return isValid;
			}
			isValid = true;
			return isValid;
		}

		// diagonal checks
		if (Math.abs(qx2 - qx1) == Math.abs(qy2 - qy1)) { // when coordinates on the y= 1*x + c, check diagonally
			if ((qx1 < qx2) && (qy1 < qy2)) {
				for (int i = qx1 + 1, j = qy1 + 1; i <= qx2 && j <= qy2; i++, j++) { // q1 to q2(from left to right,
																						// bottom to top)
					if (board[i][j] != 0)
						return isValid;
				}
			} else if ((qx1 < qx2) && (qy1 > qy2)) {
				for (int i = qx1 + 1, j = qy1 - 1; i <= qx2 && j <= qy2; i++, j--) { // q1 to q2(from left to right, top
																						// to bottom)
					if (board[i][j] != 0)
						return isValid;
				}

			} else if ((qx1 > qx2) && (qy1 < qy2)) {
				for (int i = qx2 + 1, j = qy2 + 1; i <= qx1 && j <= qy1; i++, j++) { // q2 to q1(from left to right,
																						// bottom to top)
					if (board[i][j] != 0)
						return isValid;
				}

			} else if ((qx1 > qx2) && (qy1 > qy2)) {
				for (int i = qx2 + 1, j = qy2 - 1; i <= qx1 && j <= qy1; i++, j--) { // q2 to q1(from left to right, top
																						// to bottom)
					if (board[i][j] != 0)
						return isValid;
				}

			}
			isValid = true;
			return isValid;
		}

		return isValid;
	}

}
