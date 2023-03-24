package GameState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGameBoardState {
	
	static GameBoardState board;
	static  GameBoardState board10x10;
	static GameBoardState boardOrientation;
	static int[][] gameboard = {{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
								{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
								{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
								{ 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, } };
	
	//Initialize a game board with no queens
	@BeforeEach
	public void setup() {
		ArrayList<Integer> startState = new ArrayList<>();
		for(int x =0; x < GameBoardState.BORAD_DEMENSIONS;x++) {
			for(int y = 0; y < GameBoardState.BORAD_DEMENSIONS;y++) {
				startState.add(0);
			}
		}
		board = new GameBoardState(startState);
		
		
		
		
	}
	//test to make sure setting a position and its value work within the range 
	@Test
	public void TestsetPosValue() {
		board.print();
		board.setPosValue(3, 9, 0);
		board.print();
		try {
			board.setPosValue(3, 10, 9);
		}catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		try {
			board.setPosValue(3, 9, -1);
		}catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}

	}
	
	//test to make sure that the input for game board is a 11x11 board
	//or reject the board otherwise
	@Test
	public void TestBoardDenensions() {
		try {
		board10x10 = new GameBoardState(new int[11][11]);
		}catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		assertTrue(board.getCurBoard()[0].length ==10);
		
		
	
	}
	@Test
	public void TestUpdateQueens() {
		board = new GameBoardState(gameboard);
		//board.setPosValue(2,2, 2);
		board.updateQueenPoses();
		assertEquals(board.getQueenPosition2().size(), 4);
		//board.setPosValue(1, 3, 4);
		board.updateQueenPoses();
		assertEquals(board.getQueenPosition1().size() ,4);
		board.setPosValue(3, 1, 3);
		board.updateQueenPoses();
		assertEquals(board.getArrowPositions().size() , 1);
	}
	@Test
	public void testOrietation() {
		System.out.println(gameboard[0].length);
		GameBoardState state = new GameBoardState(gameboard);
		System.out.println("oreintation state");
		state.print();
		assertTrue(true);		
		assertEquals(state.getCurBoard()[3][0], 1);

	}
	
	
	/*
	 * test invalid move 
	 */
	@Test
	public void testLegalMove() {
	    int[] old = board.setPos(2,2, 2);
		int[] oldRm = board.setPos(0,2, 2);
		int[]  newQ = board.setPos(2,3, 2);
		int[] arrw = board.setPos(3,4, 2);
		
		boolean b = board.checkIfPathIsClear(old, newQ);
		assertTrue(b);
		b = board.checkIfPathIsClear(newQ, arrw);
		assertTrue(b);
//		assertTrue(board.getIfMoveIsValid(new int[] {old.ge;t(0),old.get(1)},
//				new int[] {newQ.get(0),newQ.get(1)},
//				new int[] {arrw.get(0),arrw.get(1)}));
 	}
	@Test
	public void testillegalQueenmove() {
		int[] old = board.setPos(2,2, 2);
		int[] oldRm = board.setPos(0,2, 2);
		int[]newQ = board.setPos(2,4, 1);
		int[] arrw = board.setPos(3,4, 2);
	
		boolean b = board.checkIfPathIsClear(old, newQ);
		assertTrue(!b);
		b = board.checkIfPathIsClear(newQ, arrw);
		assertTrue(b);
//		assertTrue(!board.getIfMoveIsValid(new int[] {old.get(0),old.get(1)},
//										new int[] {newQ.get(0),newQ.get(1)},
//										new int[] {arrw.get(0),arrw.get(1)}));
		//assertTrue(board.checkIfPathIsClear( new int[] {newQ.get(0),newQ.get(1)}, new int[] {arrw.get(0),arrw.get(1)}));
	}
	@Test
	public void testIlllegalArrw() {
		int[] old = board.setPos(2,2, 2);
		int[] oldRm = board.setPos(0,2, 2);
		int[] newQ = board.setPos(2,3, 2);
		int[] arrw = board.setPos(3,9, 1);
	
		boolean b = board.checkIfPathIsClear(old, newQ);
		assertTrue(b);
		b = board.checkIfPathIsClear(newQ, arrw);
		assertTrue(!b);
		}
	
	
}
