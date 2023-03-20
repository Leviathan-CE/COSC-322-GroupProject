package GameState;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ActionFactory.Node;
import ubc.cosc322.MoveSequence;

public class TestMoveSequence {
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

	@Test
	public void TestQueenExists() {

		System.out.println(gameboard[0].length * gameboard[1].length);
		Node node = new Node(gameboard);
		node.print();

		node = MoveSequence.GenerateMove(node, 1);
		node.print();
		
		int[] move = node.moveInfo.getOldQPos();
		assertTrue(gameboard[move[0]][move[1]] == 1);

		node = MoveSequence.GenerateMove(node, 2);
		node.print();
		
		move = node.moveInfo.getOldQPos();
		assertTrue(gameboard[move[0]][move[1]] == 2);

	}
}
