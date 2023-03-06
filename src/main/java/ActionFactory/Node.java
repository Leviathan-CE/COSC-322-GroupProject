package ActionFactory;

import java.util.ArrayList;
import java.util.List;

import GameState.GameBoardState;
import Search.TreeSearch.node;


public class Node extends GameBoardState {
	
		
		int id = 1;
		static int count = 1;
		double value;
		Node parent;
		ArrayList<Node> children = new ArrayList<>();
		
	
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
