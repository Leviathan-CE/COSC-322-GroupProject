package GameState;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import ActionFactory.Node;
import ubc.cosc322.MoveSequence;

public class TestMoveSequence {

	@Test
	public void TestQueenExists() {
		  int[][] gameboard = {{0, 0, 0, 1, 0, 0, 1, 0, 0, 0,},
				               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
				               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
				               {1, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
				               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
				               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},				             {0, 3, 0, 0, 0, 0, 0, 0, 0, 0,},
				               {2, 0, 0, 0, 0, 0, 0, 0, 0, 2,},
				               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
				               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
				               {0, 0, 0, 2, 0, 0, 2, 0, 0, 0,}};
		 System.out.println(gameboard[0].length*gameboard[1].length);
		 Node node = new Node(gameboard);
		 MoveSequence.GenerateMove(node, 1);
		int[] move =MoveSequence.ChosenMove.moveInfo.oldQPos;
		assertTrue(gameboard[move[0]][move[1]] == 1);
	}
}
