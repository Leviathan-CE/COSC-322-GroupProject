package GameState;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestGameBoardState {
	
	static GameBoardState board;
	static  GameBoardState board10x10;
	static GameBoardState boardOrientation;
    int[][] gameboard = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 0, 1, 0, 0, 1, 0, 0, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 1, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 22, 0, 0, 0, 0, 0, 0, 0, 0,},
			             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}};
	
	//Initialize a game board with no queens
	@BeforeAll
	public static void setup() {
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
		board.setPosValue(3, 10, 10);
		board.print();
		try {
			board.setPosValue(3, 11, 10);
		}catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		try {
			board.setPosValue(3, 10, 0);
		}catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}

	}
	
	//test to make sure that the input for game board is a 11x11 board
	//or reject the board otherwise
	@Test
	public void TestBoardDenensions() {
		try {
		board10x10 = new GameBoardState(new int[10][10]);
		}catch(IndexOutOfBoundsException e) {
			assertTrue(true);
		}
		assertTrue(board.getCurBoard()[0].length ==11);
		
		
	
	}
	@Test
	public void TestUpdateQueens() {
		board.setPosValue(2,2, 2);
		board.updateQueenPoses();
		assertTrue(board.getQueenPosition2().size() == 1);
		board.setPosValue(1, 3, 4);
		board.updateQueenPoses();
		assertTrue(board.getQueenPosition1().size() == 1);
		board.setPosValue(3, 1, 3);
		board.updateQueenPoses();
		assertTrue(board.getArrowPositions().size() == 1);
	}
	@Test
	public void testOrietation() {
		System.out.println(gameboard[0].length);
		GameBoardState state = new GameBoardState(gameboard);
		System.out.println("oreintation state");
		state.print();
		assertTrue(true);
		//assertTrue(boardOrientation.getCurBoard()[1][1] == 1);

	}
	
}
