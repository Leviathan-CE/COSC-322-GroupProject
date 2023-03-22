package ActionFactory;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

import GameState.GameBoardState;
import GameState.MoveInfo;
import Search.TreeSearch;
import Search.TreeSearch.node;

import GameState.GameBoardState;

/**
 * 
 * TODO; create function that moves a queen and places a arrow on GameBoardState
 * TODO; create function that detects illegal moves.
 */
public class ActionFactory {

	/**
	 * EFFECTS: takes in the Board state and then returns the branch factor of all
	 * possible actions based off of what actions can be taken.
	 * 
	 * @param rootState is the root for which we calculate the branch factor based
	 *                  from a move set.
	 * @return all possible game states from the root
	 * 
	 */

	// This function generates all legal moves for a given player on the gameboard.
	// function assumes player will be an int of value 1 or 2
	// takes a 2d array as its input value and returns a array list of 2d arrays
	// that represent possible child states.
	// TODO Modify to work with our gameboardstate and Node - consult with team
	// members
	public static ArrayList<Node> getLegalMoves(Node node, int player) {
		// Initialize an empty ArrayList to store the legal moves.
		ArrayList<Node> legalMoves = new ArrayList<>();

		int[][] gameboard = node.getCurBoard();
		// Node games = new Node(newGameboard);
		int[] queens = node.countQueens();
		//System.out.println("queen count" + queens[0] + ";" + queens[1]);
		// Iterate over each cell on the gameboard.
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// Check if the current cell contains the player's piece.
				// this if statement should only be triggered 4 times

				if (gameboard[i][j] == player) {
					//System.out.println("i " + i + ";" + j);
					// Iterate over each possible direction that the piece can move in.
					for (int dx = -1; dx <= 1; dx++) {
						for (int dy = -1; dy <= 1; dy++) {
							// Skip over the case where both dx and dy are 0, which corresponds to not
							// moving the piece.
							if (dx == 0 && dy == 0) {
								continue;
							}

							// Initialize temporary variables to store the current position of the piece.
							int x = i;
							int y = j;

							// Move the piece in the current direction until it hits an occupied cell or the
							// edge of the gameboard.
							while (x + dx >= 0 && x + dx < 10 && y + dy >= 0 && y + dy < 10) {
								x += dx;
								y += dy;
								// If the current cell is empty, the move is legal.
								if (gameboard[x][y] == 0) {
									// Create a new copy of the gameboard and update it to reflect the move.
									int[][] newGameboard = copyGameboard(gameboard);

									newGameboard[i][j] = 0;
									newGameboard[x][y] = player;

									// Generate all possible arrow placements from the new position of the piece.
									for (int ax = -1; ax <= 1; ax++) {
										for (int ay = -1; ay <= 1; ay++) {
											// Initialize temporary variables to store the current position of the
											// arrow.
											int bx = x;
											int by = y;

											// Move the arrow in the current direction until it hits an occupied cell or
											// the edge of the gameboard.
											while (bx + ax >= 0 && bx + ax < 10 && by + ay >= 0 && by + ay < 10) {
												bx += ax;
												by += ay;
												// If the current cell is not empty, the arrow cannot be placed there.
												if (newGameboard[bx][by] != 0) {
													break;
												}
												// Create a new copy of the gameboard and update it to reflect the arrow
												// placement.
												int[][] arrowGameboard = copyGameboard(newGameboard);
												arrowGameboard[bx][by] = 3;

												Node game = new Node(arrowGameboard);												
												//store the move made in a info data class
												game.moveInfo = new MoveInfo(new int[] { i, j }, new int[] { x, y },
														new int[] { bx, by }, player);
												game.parent = node;

												// Add the new gameboard to the list of legal moves.
												legalMoves.add(game);
											}
										}
									}
								} else {
									// If the current cell is occupied, the move is not legal and we move on to the
									// next direction.
									break;
								}
							}
						}
					}
				}
			}
		}
		// add all children to parent
		for (Node n : legalMoves)
			node.addChild(n);

		// Return the list of legal moves.
		return legalMoves;
	}

	// helper function for copying 2d arrays
	private static int[][] copyGameboard(int[][] gameboard) {
		int[][] newGameboard = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				newGameboard[i][j] = gameboard[i][j];
			}
		}
		return newGameboard;
	}

}
