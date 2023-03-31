package GameState;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

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
		Node node2 = null;
		Node node3 = null;
		Node node4 = null;
		
		System.out.println(node.getID() +"\n "+node.hashCode());
		//System.out.println(node.moveInfo.getPlayerID());
		
		node2 = MoveSequence.GenerateMove(node, 1, 1);	
		System.out.println(node2.getID() +"\n "+node2.hashCode());
		System.out.println(node2.moveInfo.getPlayerID());
		//int[] move = node.moveInfo.getOldQPos();
		//assertTrue(gameboard[move[0]][move[1]] == 1);

		node3 = MoveSequence.GenerateMove(node2, 2,2);		
		System.out.println(node3.getID() +"\n "+node3.hashCode());
		System.out.println(node3.moveInfo.getPlayerID());
		//move = node.moveInfo.getOldQPos();
		//assertTrue(gameboard[move[0]][move[1]] == 2);
		
		node4 = MoveSequence.GenerateMove(node3, 1,3);		
		System.out.println(node4.getID() +"\n "+node4.hashCode());
		System.out.println(node4.moveInfo.getPlayerID());

	}
}
