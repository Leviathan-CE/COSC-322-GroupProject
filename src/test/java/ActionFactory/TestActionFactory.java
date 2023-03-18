package ActionFactory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestActionFactory {

	static Node root;
	
	// In the initial position the first player has 2176 possible moves
	
	@BeforeAll
	public static void setup () {
		// create a test node with a gameboard state
        int[][] gameboard = {{0, 0, 0, 2, 0, 0, 2, 0, 0, 0},
                             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                             {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                             {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                             {0, 0, 0, 1, 0, 0, 1, 0, 0, 0}};
        
        root = new Node(gameboard);
        
	}
	
	@Test
	public void TestGetLegalMoves() {
        
        // test player 1's legal moves
        ArrayList<Node> legalMoves = ActionFactory.getLegalMoves(root, 1);
        assertEquals(legalMoves.size(), 2176);
        
	}
		
	

	

}
