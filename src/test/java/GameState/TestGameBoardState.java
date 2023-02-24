package GameState;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestGameBoardState {
	
	static GameBoardState board;
	
	//Initialize a game board with no queens
	@BeforeAll
	public static void setup() {
		ArrayList<Integer> startState = new ArrayList<>();
		for(int x =0; x < board.BOARD_WIDTH;x++) {
			for(int y = 0; y < board.BOARD_HIEGHT;y++) {
				startState.add(0);
			}
		}
		board = new GameBoardState(startState);
	}
	
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
	
//	@Test
//	public void TestSaveBoard() {
//		board.SaveBoard();
//		for(int x =0; x < board.BOARD_WIDTH;x++) {
//			for(int y = 0; y < board.BOARD_HIEGHT;y++) {
//				if(board.currentBoard[x][y] != board.savedBoard[x][y])
//					assertTrue(false);
//			}
//		}
//		assertTrue(true);
//	}
	
}
