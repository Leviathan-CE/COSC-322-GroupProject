package ActionFactory;

import java.util.ArrayList;
import java.util.List;

import GameState.GameBoardState;
import Search.TreeSearch.node;


public class Node extends GameBoardState {
	
		
		int id = 1;
		static int count = 1;
		
		private double M =0; //monte-carlo value
		private double h =0; //hueristic
		private double g =0; //g-val
		public double C = 1; //carlo constant
		Node parent;
		ArrayList<Node> children = new ArrayList<>();
		
		
		// F(n) = g+h+M 
		public double getValue() {return (g+h)+(M*C);}
		public void setUtilScore(double newVal) {M = newVal;} //monteCarlo value
		public void setHueristicScore(double newVal) {h = newVal;}
		public void setGval(double newVal){g=newVal;}
		
		public Node(ArrayList<Integer> gameBoard) {
			super(gameBoard);
			id= count++;
		}
		public Node(int[][] gameBoard) {
			super(gameBoard);
			id= count++;
		}
		public String toString() {
			return "" + id;
		}

		public void setParent(Node node) {
			parent = node;
		}
		public void addChild(Node node) {
			children.add(node);
		}		
		

	
}
