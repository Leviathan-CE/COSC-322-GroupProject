package GameState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class tetsHeuristic6 {

	
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
	
	static int[][] gameboardFilled = {{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, },
									  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
									  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
									  { 1, 3, 3, 3, 3, 3, 3, 0, 0, 1, },
								      { 0, 3, 0, 3, 0, 0, 3, 0, 0, 0, },
									  { 0, 3, 3, 0, 2, 0, 3, 3, 0, 0, },
								 	  { 2, 3, 0, 0, 3, 3, 0, 3, 0, 2, },
									  { 0, 3, 3, 3, 3, 3, 0, 3, 0, 0, },
									  { 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, },
									  { 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, } };
	@Test
	public void testH6() {
		Node n = new Node(gameboardFilled);
		n.moveInfo = new MoveInfo(null, new int[] {4,4}, null, count);
		assertTrue(9 == n.H6());
		int[][] copy = new int[10][10];
		for(int x =0; x< 9; x++) {
			for( int y=0; y < 9; y++){
				if(n.getCurBoard()[x][y] == -1)
					assertTrue(false);
			}
		}
	}
	
	@Test
	public void testgetArea() {
		int[][] copy = new int[10][10];
		for(int x =0; x< 9; x++) {
			for( int y=0; y < 9; y++){
				copy[x][y] = gameboardFilled[x][y];
			}
		}
		
		int count = getArea(new int[]{4,4},copy);
		System.out.println(toString());
		assertEquals(count,9 );
		
	}
	
	public String toString() {
		String msg = "";
		for (int y = 0; y < 10; y++) {
			msg += "\n";
			for (int x = 0; x < 10; x++) {
				msg += "  " + gameboardFilled[9 - y][x];
			}
		}

		return msg;
	}
	int count = 0;
	public int getArea(int[] Pos, int[][] board) {
		
		// if out of bounds return
		if(Pos[0] < 0 || Pos[1] < 0 || Pos[0] > 9 || Pos[1] > 9)
			return count;
		
		
		//if not empty return
	    if (board[Pos[0]][Pos[1]] != 0) {
	        return count;
	    }
	    board[Pos[0]][Pos[1]] = -1;
	    
	    count++;
	    //go threw adjacent squares and count them
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {	
						//exclude center square
						if(x != 0 || y != 0) {							
							getArea(new int[] {Pos[0]+x,Pos[1]+y}, board);					
						}
				}
			}
		return count;
		
		
	}
}
