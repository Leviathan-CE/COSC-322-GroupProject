package MonteCarlo;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import ActionFactory.Node;
import monteCarlo.mctsUpgraded;

public class TestCarlo {

	 int[][] board = {{3,  2,  3,  3,  0,  0,  0,  3,  3,  3},
			  {3,  3,  3,  3,  3,  3,  3,  3,  3,  0},
			  {3,  3,  3,  0,  3,  0,  0,  3,  1,  3},
			  {0,  3,  0,  0,  3,  2,  3,  3,  3,  3},
			  {3,  3,  1,  3,  3,  3,  3,  3,  3,  3},
			  {0,  0,  3,  0,  3,  3,  0,  1,  3,  3},
			  {3,  3,  3,  3,  3,  3,  3,  3,  0,  1},
			  {3,  0,  3,  3,  3,  3,  0,  0,  3,  3},
			  {3,  3,  3,  3,  2,  3,  3,  3,  3,  3},
			  {3, 3,  3,  0,  2,  0,  3,  3,  3,  3}};
	 @Test
	 public void testNullreturnNode() {
		 Node n = new Node(board);
		 Node chosen = mctsUpgraded.getMonteMove(n, 1);
		 assertTrue(chosen != null);
	 }
}
