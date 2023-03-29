package GameState;

import java.io.Serializable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import ActionFactory.Node;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * GameState class which holds a local game state represented as a 2d matrix.
 * the game state values.
 * 
 * @Note Expected Range of values 0 = empty 2 = white queen 1 = black queen 3 =
 *       arrow
 * 
 * @Note: size is not 10 because input also includes the labeling from 1-10 and
 *        a-j for gui which is why BOARD_WIDTH and BOARD_HIEGHT are 11.
 * 
 * @param BOARD_DEMENSIONS = 11
 * @param INTERNAL_STATE   = 10 : the default game board size
 * 
 * 
 *
 */
public class GameBoardState implements Serializable {

	private static final long serialVersionUID = 1004773758902058794L;
	public final static int BORAD_DEMENSIONS = 11;
	public final static int INTERANL_STATE = 10;

	public MoveInfo moveInfo = null;

	private String ID = "";
	private int hashCode = -1;
	private int[][] currentBoard = null;

	private ArrayList<int[]> queenPosBlack1 = new ArrayList<>();
	private ArrayList<int[]> queenPosWhite2 = new ArrayList<>();
	private ArrayList<int[]> arrowsPos = new ArrayList<>();

	/**
	 * @CONSTRUCTOR : take in a 121 arraylist of integer and transforms it into a
	 *              readable 11x11 game board were positions are located between
	 *              then down-sized to a 10x10 gamebaord.
	 * @EFFECTS: currentBaord, creates 2d array representation of the game board.
	 * @Modifies: currentBoard
	 * 
	 * @param gameBaord is list of integers collected from
	 *                  AmazonsGameMessage.GAME_STATE
	 */
	public GameBoardState(ArrayList<Integer> gameBoard) {
		ID = "";
		System.out.println(gameBoard.toString());
		currentBoard = new int[11][11];
		for (int y = 1; y < BORAD_DEMENSIONS; y++) {
			for (int x = 1; x < BORAD_DEMENSIONS; x++) {
				currentBoard[y][x] = Integer.valueOf(gameBoard.get(y * 11 + x));
				ID = ID + String.valueOf(Integer.valueOf(gameBoard.get(y * 11 + x)));
			}
		}
		int[][] down_size = new int[10][10];
		for (int y = 9; y >= 0; y--) {
			for (int x = 9; x >= 0; x--) {
				down_size[y][x] = currentBoard[y + 1][x + 1];
			}
		}
		currentBoard = down_size;

	}

	/**
	 * @CONSTRUCTOR : create a new game state from a game board that is either 11x11
	 *              to be down-sized to 10x10 or deep copy a 10x10 game board
	 * 
	 * @param gameBoard
	 */
	public GameBoardState(int[][] gameBoard) {
		currentBoard = new int[10][10];
		ID = "";
		if (gameBoard[0].length == 10) {
			for (int y = 9; y >= 0; y--) {
				for (int x = 9; x >= 0; x--) {
					currentBoard[y][x] = gameBoard[y][x];
					ID = ID + gameBoard[y][x];
				}
			}
//			for (int y = 0; y < INTERANL_STATE; y++) {
//				for (int x = 0; x < INTERANL_STATE; x++) {
//
//					currentBoard[y][x] = gameBoard[y][x];
//				}
//			}
		} else { // if board state is not 10x10 throw error
			throw new IndexOutOfBoundsException("game Board must be of demensions of 10x10");
		}

	}

	// ------Helper Methods-----------

	public boolean equals(Node node) {
		if (this.ID.contentEquals(node.getID()))
			return true;
		return false;
	}

	/**
	 * EFFECTS: sums the values in each row by the rows index and then sums the
	 * column by the column index {X1j +X2j + xnj} * Xj
	 */
	@Override
	public int hashCode() {
		int total = 0;
		if (hashCode == -1) {
			for (int y = 1; y < BORAD_DEMENSIONS; y++) {
				for (int x = 1; x < BORAD_DEMENSIONS; x++) {
					total += y * x;
					total += x * y;
				}

			}
			hashCode = total;
			return total;
		}
		return hashCode;
	}

	public String getID() {
		return ID;
	}

	/**
	 * print to console currentBoard as matrix prints the representation with the
	 * square 1a as top left corner. which makes the console representation flip'd
	 * on the x-axs in relation to the GUI.
	 */
	public void print() {
		System.out.println(toString());
	}

	public int[][] getCurBoard() {
		return currentBoard;
	}

	public void printQPoses() {
		for (int i = 0; i < queenPosBlack1.size(); i++) {
			System.out.print("1:Black :: " + Arrays.toString(queenPosBlack1.get(i)));

		}
		System.out.println();
		for (int i = 0; i < queenPosWhite2.size(); i++) {
			System.out.print("2:White :: " + Arrays.toString(queenPosWhite2.get(i)));

		}
		System.out.println();
	}

	public ArrayList<int[]> getQueenPosition1() {
		return queenPosBlack1;
	}

	public ArrayList<int[]> getQueenPosition2() {
		return queenPosWhite2;
	}

	public ArrayList<int[]> getArrowPositions() {
		return arrowsPos;
	}

	@Override
	public String toString() {
		String msg = "";
		for (int y = 0; y < 10; y++) {
			msg += "\n";
			for (int x = 0; x < 10; x++) {
				msg += "  " + currentBoard[9 - y][x];
			}
		}

		return msg;

	}

	/**
	 * @EFFECTS: sets new value of of board x position and y position MODIFIES:
	 *           currentBaord
	 * 
	 * @param newValue the new value that will be set in the matrix
	 * @param x        is the x integer value position of a column 1-10
	 * @param y        is the y integer value position of a row 1-10
	 */
	public ArrayList<Integer> setPosValue(int newvalue, int x, int y) {
//		if (x < 1 || y < 1 || x > 10 || y > 10)
//			throw new IndexOutOfBoundsException("index must be between 1-11 inclusive");
		currentBoard[x][y] = newvalue;
		ArrayList<Integer> posVal = new ArrayList<Integer>((Arrays.asList(x, y)));
		return posVal;
	}

	public int[] setPos(int newvalue, int x, int y) {
		if (x < 0 || y < 0 || x > 9 || y > 9)
			throw new IndexOutOfBoundsException("index must be between 1-11 inclusive");
		currentBoard[x][y] = newvalue;
		return new int[] { x, y };
	}

	/**
	 * @deprecated To be Removed
	 * @EFFECTS: Moves a single Queen and shoots a arrow. MODIFIES: queenPosWhite2
	 *           or queenPosBlack1, CurrentBoard
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
//		if (currentBoard[oldPosXY[0]][oldPosXY[1]] != queenColor) {
//			throw new IndexOutOfBoundsException("that is not a vaible queen to move. queen: " + queenColor
//					+ " :: pos: [" + oldPosXY[0] + "," + oldPosXY[1] + "]");
//		}
//		if (newPosXY[0] < 0 || newPosXY[1] < 0 || newPosXY[0] > 9 || newPosXY[1] > 9)
//			throw new IndexOutOfBoundsException("index must be between 1-11 inclusive");

		// retrieve index of queen that moved
		int queenIndex = 0;
		if (queenColor == 1) {
			for (int i = 0; i < 4; i++)
				if (queenPosBlack1.get(i)[0] == oldPosXY[0] && queenPosBlack1.get(i)[1] == oldPosXY[1]) {
					queenIndex = i;
					break;
				}
		}

		if (queenColor == 2) {
			for (int i = 0; i < 4; i++)
				if (queenPosWhite2.get(i)[0] == oldPosXY[0] && queenPosWhite2.get(i)[1] == oldPosXY[1]) {
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
			queenPosBlack1.get(queenIndex)[0] = newPosXY[0];
			queenPosBlack1.get(queenIndex)[1] = newPosXY[1];
		}
		if (queenColor == 2) {
			queenPosWhite2.get(queenIndex)[0] = newPosXY[0];
			queenPosWhite2.get(queenIndex)[1] = newPosXY[1];
		}
		// return sender object data
		return senderObj;

	}

	/**
	 * @EFFECTS: count the the number of queens on both sides expected their should
	 *           always be four of each.
	 * 
	 * @return returns 2d array position 0 is white queen position 1 is black queen
	 */
	public int[] countQueens() {
		int Queen1 = 0;
		int Queen2 = 0;
		for (int i = 0; i < INTERANL_STATE; i++) {
			for (int j = 0; j < INTERANL_STATE; j++) {
				if (currentBoard[i][j] == 1)
					Queen1++;
				if (currentBoard[i][j] == 2)
					Queen2++;
			}
		}
		// System.out.println("q1 = " + Queen1 + " q2 = " + Queen2);
		return new int[] { Queen1, Queen2 };
	}

	public void updateQueenPoses() {
		ArrayList<int[]> q1w = new ArrayList<>(4);
		ArrayList<int[]> q2b = new ArrayList<>(4);
		ArrayList<int[]> arrw = new ArrayList<>();

		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (currentBoard[y][x] == 1) {
					q1w.add(new int[] { y, x });
				}
				if (currentBoard[y][x] == 2) {
					q2b.add(new int[] { y, x });
				}
				if (currentBoard[y][x] == 3) {
					arrw.add(new int[] { y, x });
				}

			}

		}

		queenPosBlack1 = q1w;
		queenPosWhite2 = q2b;
		arrowsPos = arrw;
	}
	/**
	 * @TODO: horizontal paths are not checked only does diagnol
	 * 
	 * @param pos1
	 * @param pos2
	 * @return
	 */
	public boolean checkIfPathIsClear(int[] pos1, int[] pos2) {
		boolean isValid = false;
		// Check if both positions are on the same row, column, or diagonal
		if (pos1[0] == pos2[0] || pos1[1] == pos2[1] || Math.abs(pos1[0] - pos2[0]) == Math.abs(pos1[1] - pos2[1])) {
			// Check if there are any pieces in the way
			int startX = Math.min(pos1[0], pos2[0]);
			int startY = Math.min(pos1[1], pos2[1]);
			int endX = Math.max(pos1[0], pos2[0]);
			int endY = Math.max(pos1[1], pos2[1]);
			for (int x = startX; x <= endX; x++) {
				for (int y = startY; y <= endY; y++) {
					if (x == pos1[0] && y == pos1[1]) {
						// Ignore the queen's starting position
						continue;
					}
					if (currentBoard[x][y] != 0) {
						// There's a piece in the way
						isValid = false;
						;
					}
				}
			}
			// The path is clear
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;

	}

//	public boolean getIfMoveIsValid(int[] oldQ, int[] newQ, int[] arrw) {
////			if(checkIfPathIsClear(oldQ,newQ)) {
////				if(checkIfPathIsClear(newQ,arrw)) {
////					return true;
////				}
////			}
////			return false;
//		
//				if (ifMoveIsValid(oldQ[0],oldQ[1],newQ[0], newQ[1]) && /// check if it is valid to move from (qx1,qy1) to (qx2, qy2)
//			ifMoveIsValid(newQ[0], newQ[1], arrw[0], arrw[1])) { // then check if it is valid to shot arrow from (qx2, qy2) to (ax,
//													// ay)
//				return true;
//			}
//		
//
//		return false;
//	}
//
//	public boolean ifMoveIsValid(int qx1, int qy1, int qx2, int qy2) {
//		boolean isValid = false;
//		int[][] board = currentBoard;
//		if ((qy1 == qy2) && (qx1 == qx2)) { // when it is located at the same coordinate.
//			return isValid;
//		}
//
//		if (qy1 == qy2) { // when coordinates of y is same, check vertically
//
//			if (qx1 < qx2) {
//				for (int i = qx1 + 1; i <= qx2; i++) { // this for loop checks the vertical path of the queen from start
//					// to end and checks to make sure that path is clear
//					if (board[i][qy1] != 0)
//						return false;
//				}
//			} else if (qx1 > qx2) {
//				for (int i = qx1 - 1; i >= qx2; i--) { // this for loop checks the vertical path of the queen from start
//					// to end and checks to make sure that path is clear
//					if (board[i][qy1] != 0)
//						return false;
//				}
//			}
//			System.out.println("is not true");
//			isValid = true;
////			return isValid;
//		}
//
//		if (qx1 == qx2) { // when coordinates of x is same, check horizontally
//
//			if (qy1 < qy2) {
//				for (int i = qy1 + 1; i <= qy2; i++) { // this for loop checks the horizontal path of the queen from
//														// start
//					// to end and checks to make sure that path is clear
//					if (board[qx1][i] != 0)
//						return false;
//				}
//			} else if (qy1 > qy2) {
//				for (int i = qy1 - 1; i >= qy2; i--) { // this for loop checks the horizontal path of the queen from
//														// start
//					// to end and checks to make sure that path is clear
//					if (board[qx1][i] != 0)
//						return false;
//				}
//
//			}
//			System.out.println("is not true");
//			isValid = true;
////			return isValid;
//		}
//
//		// diagonal checks
//		if (Math.abs(qx2 - qx1) == Math.abs(qy2 - qy1)) { // when coordinates on the y= 1*x + c, check diagonally
//			if ((qx1 < qx2) && (qy1 < qy2)) {
//				for (int i = qx1 + 1, j = qy1 + 1; i <= qx2 && j <= qy2; i++, j++) { // q1 to q2(from left to right,
//																						// bottom to top)
//					if (board[i][j] != 0)
//						return false;
//				}
//			} else if ((qx1 < qx2) && (qy1 > qy2)) {
//				for (int i = qx1 + 1, j = qy1 - 1; i <= qx2 && j >= qy2; i++, j--) { // q1 to q2(from left to right, top
//																						// to bottom)
//					if (board[i][j] != 0)
//						return false;
//				}
//
//			} else if ((qx1 > qx2) && (qy1 < qy2)) {
//				for (int i = qx1 - 1, j = qy1 + 1; i >= qx2 && j <= qy2; i--, j++) { // q2 to q1(from left to right,
//																						// bottom to top)
//					if (board[i][j] != 0)
//						return false;
//				}
//
//			} else if ((qx1 > qx2) && (qy1 > qy2)) {
//				for (int i = qx1 - 1, j = qy1 - 1; i >= qx2 && j >= qy2; i--, j--) { // q2 to q1(from left to right, top
//																						// to bottom)
//					if (board[i][j] != 0)
//						return false;
//				}
//
//			}
////			isValid = true;
////			return isValid;
//		}
//
//		return isValid;
//	}

	/*
	 * heuristic1: looks at all the tiles around the queens. If the tiles dont = 1
	 * then we add to each teams score we then return sumEnemyteam - sumOurteam
	 */
	public double H1(int queenColor) {

		int sumOfWhiteQueen = 1;
		int sumOfBlackQueen = 1;
		int[][] board = currentBoard;
		// get WHite queeen value

		for (int[] queen : queenPosWhite2) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					if (!(x == 0 && y == 0)) { // if tile is queen tile then skip

						if (queen[0] + x >= 0 && queen[0] + x < 10
								&& queen[1] + y >= 0 && queen[1] + y < 10) {
							if (board[queen [0] + x][queen[1] + y ] == 0) {
								sumOfWhiteQueen++;
							} 

						}
					}
				}
			}

		}
		for (int[] queen : queenPosBlack1) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					if (!(x == 0 && y == 0)) { // if tile is queen tile then skip
						if (queen[0] + x >= 0 && queen[0] + x < 10 && queen[1] + y >= 0 && queen[1] + y < 10) {
							if (board[queen[0] + x][queen[1] + y] == 0) {
								sumOfBlackQueen++;
							}
						}
					}
				}
			}

		}

		if (queenColor == 1) {
			double sum;
			if(sumOfWhiteQueen ==0 ) {
				sumOfWhiteQueen=1;
			}
			if(sumOfBlackQueen>sumOfWhiteQueen ) {
				//System.out.println("black is bigger");
				sum =((double) sumOfBlackQueen/(double)sumOfWhiteQueen) ;
			}else {
				sum =((double) sumOfBlackQueen/(double)sumOfWhiteQueen *10);
				//System.out.println("white is bigger");
			}
			return sum;
		} else if(queenColor == 2) {
			double sum =  (double)sumOfWhiteQueen- (double) sumOfBlackQueen;
			if(sum <0 ) {
				sum =0;
			}
			return sum;
		}
		return 0;

	}

//getAllPossibleMoves(2).size()- getAllPossibleMoves(1).size()- 
//this heuristic will see all the moves the other team can make minus the move we can make 
	public double H2(int QueenColor) {
		if (QueenColor == 1) {
			return getAllPossibleMoves(1).size() / (double) getAllPossibleMoves(2).size();
		} else {
			return getAllPossibleMoves(2).size() / (double) getAllPossibleMoves(1).size();
		}

	}

	public ArrayList<int[]> getAllPossibleMoves(int colour) {
		ArrayList<int[]> movesList = new ArrayList<>();
		ArrayList<int[]> QueenPosition = null;
		if (colour == 1) {
			QueenPosition = queenPosBlack1;
		} else if (colour == 2) {
			QueenPosition = queenPosWhite2;
		}
		for (int[] CurrentPositionOfQueen : QueenPosition) {
			ArrayList<int[]> allMovesForCurrentQueen = getAllMoves(CurrentPositionOfQueen[0],
					CurrentPositionOfQueen[1]);
			for (int[] CheckMove : allMovesForCurrentQueen) {
				ArrayList<int[]> ArrowMove = getAllMoves(CheckMove[0], CheckMove[1]);
				for (int[] arrow : ArrowMove)
					movesList.add(new int[] { CurrentPositionOfQueen[0], CurrentPositionOfQueen[1], CheckMove[0],
							CheckMove[1], arrow[0], arrow[1] });
			}
		}
		return movesList;
	}

	public ArrayList<int[]> getAllMoves(int x, int y) {
		ArrayList<int[]> list = new ArrayList<>();
		list.addAll(getAllMoves(x, y, 0, -1, new ArrayList<int[]>())); // up
		list.addAll(getAllMoves(x, y, 1, -1, new ArrayList<int[]>())); // topright
		list.addAll(getAllMoves(x, y, 1, 0, new ArrayList<int[]>())); // right
		list.addAll(getAllMoves(x, y, 1, 1, new ArrayList<int[]>())); // bottomright
		list.addAll(getAllMoves(x, y, 0, 1, new ArrayList<int[]>())); // down
		list.addAll(getAllMoves(x, y, -1, 1, new ArrayList<int[]>())); // downleft
		list.addAll(getAllMoves(x, y, -1, 0, new ArrayList<int[]>())); // left
		list.addAll(getAllMoves(x, y, -1, -1, new ArrayList<int[]>())); // upleft
		return list;
	}

	public ArrayList<int[]> getAllMoves(int x, int y, int IncreaseX, int IncreaseY, ArrayList<int[]> list) {
		if (x + IncreaseX >= 0 && x + IncreaseX < 10 && y + IncreaseY >= 0 && y + IncreaseY < 10) {// checking to see if
																									// the value is out
																									// of bounds
			list.add(new int[] { x + IncreaseX, y + IncreaseY }); // if the value isnt out of bounds continue
			return getAllMoves(x + IncreaseX, y + IncreaseY, IncreaseX, IncreaseY, list); // recurse
		} else
			return list;
	}

	/**
	 * heuristic 3 is a kill heuristic that only triggers when an emeny queen is
	 * surrounded by arrow except a single tile which is the 8 tile and is the move
	 * this board represents if it does apply the wieghted value to the Node
	 * 
	 * @param color  : out team color
	 * @param wieght : how much priority this move will have over others
	 * @return
	 */
	public double H3(int color, double wieght) {
		ArrayList<int[]> QueenPos = new ArrayList<int[]>();
		if (color == 1) {
			QueenPos = queenPosWhite2;
		} else {
			QueenPos = queenPosBlack1;
		}
		for (int[] qn : QueenPos) {
			int AreaAroundQueen = 0;
			boolean ourArrow = false;
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					if (!(x == 0 & y == 0)) {

						if (qn[0] + x >= 0 && qn[0] + x < 10 && qn[1] + y >= 0 && qn[1] + y < 10) {
							if (currentBoard[qn[0] + x][qn[1] + y] == 3) {
								AreaAroundQueen++;
							}
							if (qn[0] + x == moveInfo.getArrow()[0] && qn[1] + y == moveInfo.getArrow()[1]) {
								ourArrow = true;
							}
						} else {
							AreaAroundQueen++;
						}

					}
				}
			}
			if (AreaAroundQueen == 8 && ourArrow) {
				return wieght;
			}
		}

		return 0;
	}
	/**
	 * Arrow placer hueristic that give priority to moves that place
	 * an arrow adjacent to a enemy queen
	 * @param color
	 * @return number blocked tiles around enemy queen
	 */
	public double H5(int color) {
		ArrayList<int[]> QueenPos = new ArrayList<int[]>();
		if (color == 1) {
			QueenPos = queenPosWhite2;
		} else {
			QueenPos = queenPosBlack1;
		}
		for (int[] qn : QueenPos) {
			int AreaAroundQueen = 0;
			boolean ourArrow = false;
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					if (!(x == 0 & y == 0)) {

						// if inside bounds and position around queen is not empty
						if (qn[0] + x >= 0 && qn[0] + x < 10 && qn[1] + y >= 0 && qn[1] + y < 10) {
							if (currentBoard[qn[0] + x][qn[1] + y] != 3) {
								AreaAroundQueen++;
							}
							// if our arrow is adjacent to the queen
							if (qn[0] + x == moveInfo.getArrow()[0] && qn[1] + y == moveInfo.getArrow()[1]) {
								ourArrow = true;
							}
						} else {
							AreaAroundQueen++;
						}

					}
				}
			}
			if (ourArrow) {
				return AreaAroundQueen;
			}
		}

		return 0;
	}
	/**
	 * escape heuristic that determines how much free space is around the current move vs its old move
	 * @return
	 */
	public double H4() {
		ArrayList<int[]> AllMovesNew = getAllMoves(moveInfo.getNewQPos()[0],moveInfo.getNewQPos()[1]);
		ArrayList<int[]> AllMovesOld = getAllMoves(moveInfo.getOldQPos()[0],moveInfo.getOldQPos()[1]);
		
		if(AllMovesNew.size() - AllMovesOld.size() <-1 ) {
			return 0;
		}else if(AllMovesNew.size() - AllMovesOld.size() ==0){
			return 1;
		}else {
			return AllMovesNew.size() - AllMovesOld.size(); 
		}

	
	}
	private int countA = 0; // for use in getArea only
	/**
	 * counts the number of space it can possibly go to
	 * @return total space it can go
	 */
	public double H6() {
		int[][] copy = new int[10][10];
		for(int x =0; x< 9; x++) {
			for( int y=0; y < 9; y++){
				copy[x][y] = currentBoard[x][y];
			}
		}
		   countA = 0;
	  	  return getArea(moveInfo.getNewQPos(),copy);
	}
	
	/**
	 * @EFFECTS recursively counts all empty tiles a position can reach
	 * @REQUIRES a deep copy of the board to avoid changing its state
	 * 
	 * @param Pos the starting location
	 * @param board  copy of the board 
	 * @param empties empty array
	 * @return count of the empty tiles that position can reach
	 */
	private int getArea(int[] Pos, int[][] board) {
		
		// if out of bounds return
		if(Pos[0] < 0 || Pos[1] < 0 || Pos[0] > 9 || Pos[1] > 9)
			return countA;
		
		
		//if not empty return
	    if (board[Pos[0]][Pos[1]] != 0) {
	        return countA;
	    }
	    board[Pos[0]][Pos[1]] = -1;	    
	    countA++;
	    //go threw adjacent squares and count them
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {	
						//exclude center square
						if(x != 0 || y != 0) {							
							getArea(new int[] {Pos[0]+x,Pos[1]+y}, board);					
						}
				}
			}
		return countA;
	}

//	public double H4(int team) {
//		ArrayList<int[]> AllMovesNew = getAllMoves(moveInfo.getNewQPos()[0], moveInfo.getNewQPos()[1]);
//		ArrayList<int[]> AllMovesOld = getAllMoves(moveInfo.getOldQPos()[0], moveInfo.getOldQPos()[1]);
//
//		ArrayList<Vector2> allQueenCoordsWhite = new ArrayList<>();
//		ArrayList<Vector2> allQueenCoordsBlack = new ArrayList<>();
//		ArrayList<Vector2> queensToSkipWhite = new ArrayList<>();
//		ArrayList<Vector2> queensToSkipBlack = new ArrayList<>();// Queens will be added to list if they are to be
//																	// prevented from having BFS performed on them.
//		int spaceAvailableToQueensWhite = 0;
//		int spaceAvailableToQueensBlack = 0;
//		int[][] board = currentBoard;
//		for (int[] transfer : queenPosWhite2) {
//			allQueenCoordsWhite.add(new Vector2(transfer));
//		}
//		for (int[] transfer : queenPosBlack1) {
//			allQueenCoordsBlack.add(new Vector2(transfer));
//		}
//
//		for (Vector2 queenCoords : allQueenCoordsWhite) {
//			if (queensToSkipWhite.contains(new Vector2(queenCoords)))
//				continue;
//
//			LinkedList<Vector2> tileQueue = new LinkedList<>();
//			HashSet<Vector2> tilesVisited = new HashSet<>();
//			tileQueue.add(queenCoords);
//
//			// Breadth-first search
//			while (!tileQueue.isEmpty()) {
//				ArrayList<Vector2> tilesToAdd = new ArrayList<>();
//
//				System.out.println(tileQueue.size());
//				tilesToAdd.addAll(getTilesAround(tileQueue.poll())); // Dequeue current tile, add its children to
//																		// shortlist of tiles to expand upon
//				for (Vector2 tile : tilesToAdd)
//					// for each shortlisted tile,
//					if (tile.getx() >= 0 && tile.getx() < 10 && tile.gety() >= 0 && tile.gety() < 10) {
//						if (!tilesVisited.contains(new Vector2(tile)) && board[tile.getx()][tile.gety()] == 0) { // if
//																													// it
//																													// is
//																													// empty
//																													// and
//																													// has
//																													// not
//																													// been
//																													// visited,
//							tileQueue.add(tile); // add tile to queue
//							tilesVisited.add(new Vector2(tile)); // mark tile as visited
//							spaceAvailableToQueensWhite++; // Now, if the current tile is empty, add it to list of
//															// available free tiles.
//						}
//
//					}
//			}
//		}
//		for (Vector2 queenCoords : allQueenCoordsBlack) {
//			if (queensToSkipBlack.contains(new Vector2(queenCoords)))
//				continue;
//
//			LinkedList<Vector2> tileQueue = new LinkedList<>();
//			HashSet<Vector2> tilesVisited = new HashSet<>();
//			tileQueue.add(queenCoords);
//			System.out.println("doingthis");
//			// Breadth-first search
//			while (!tileQueue.isEmpty()) {
//				ArrayList<Vector2> tilesToAdd = new ArrayList<>();
//				System.out.println("begining of while loop");
//				tilesToAdd.addAll(getTilesAround(tileQueue.poll())); // Dequeue current tile, add its children to
//																		// shortlist of tiles to expand upon
//				for (Vector2 tile : tilesToAdd) // for each shortlisted tile,
//					if (!tilesVisited.contains(new Vector2(tile)) && board[tile.getx()][tile.gety()] == 0) { // if it is
//																												// empty
//																												// and
//																												// has
//																												// not
//																												// been
//																												// visited,
//						tileQueue.add(tile); // add tile to queue
//						tilesVisited.add(new Vector2(tile)); // mark tile as visited
//						spaceAvailableToQueensBlack++; // Now, if the current tile is empty, add it to list of available
//														// free tiles.
//					}
//				System.out.println(tileQueue.size());
//			}
//		}
//		return spaceAvailableToQueensBlack;
//
//		if (team == 1) {
//			return spaceAvailableToQueensBlack;
//
//			if (AllMovesNew.size() - AllMovesOld.size() < -1) {
//				return 0;
//			} else if (AllMovesNew.size() - AllMovesOld.size() == 0) {
//				return 1;
//
//			} else {
//				return AllMovesNew.size() - AllMovesOld.size();
//			}
//
//		}
//	}
//	public  ArrayList<Vector2> getTilesAround(Vector2 vector2) {
//	ArrayList<Vector2> proxmateTiles = new ArrayList<>();
//		for (int x = -1; x <= 1; x++) {
//			for (int y = -1; y <= 1; y++) {
//				if (!(x == 0 && y == 0)) { // if tile is queen tile then skip
//					if (vector2.getx() + x >= 0 && vector2.gety() + x < 10
//							&& vector2.getx() + y >= 0 &&vector2.gety() + y < 10) {
//							proxmateTiles.add(new Vector2 (vector2.getx() + x, vector2.gety() + y));
//					}
//				}
//			}
//		}
//	
//	return proxmateTiles;
//	}

}
