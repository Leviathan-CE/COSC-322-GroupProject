package ActionFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ActionFactory.ActionFactory;
import ActionFactory.Node;

public class TestActionFactory {

	
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
	static int maxLegalMoves = 2176;
	static ArrayList<Node> possibilities;
	@BeforeAll
	public static void setUp() {
		possibilities= ActionFactory.getLegalMoves(new Node(gameboard), 1, false);
	}
	@Test
	public void testMaxMoveGen() {
		
		assertEquals(possibilities.size() , maxLegalMoves);
	}
}
