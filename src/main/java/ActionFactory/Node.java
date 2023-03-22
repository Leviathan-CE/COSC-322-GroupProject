package ActionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import GameState.GameBoardState;
import Search.TreeSearch.node;


public class Node extends GameBoardState  {
	
		

		int id = 1;
		static int count = 1;
		
		public double miniMaxvVal = -1000000000;
		private double M =0; //monte-carlo value
		private double h =0; //hueristic
		private double g =0; //g-val
		public double C = 1; //carlo constant
		
		private int numWins=0;
		private int visits=1; 
		private int depth = 0;
		Node parent;
		ArrayList<Node> children = new ArrayList<>();
		
		
		// F(n) = Evaluation(node) + C * root(ln(Visits(parent(node))/Visits(node)
		public double getValue() {return Math.abs((g+h)/visits + C*Math.sqrt(Math.log(parent.visits)/visits));} //UCBI Score
		public void setHueristicScore(double newVal) {h = newVal;}
		public void setGval(double newVal){g=newVal;}

		
		//-----CONSTRUCTORS-----------

		public Node(ArrayList<Integer> gameBoard) {
			super(gameBoard);			
			id= count++;
		}
		public Node(int[][] gameBoard) {
			super(gameBoard);
			id= count++;
		}
		/**
		 * @deprecated
		 * @param node
		 */
		public Node(Node node) {
			super(node.getCurBoard());
			g =node.g;
			M = node.M;
			h = node.h;
			C = node.C;
			id = node.id;
			numWins = node.numWins;
			visits = node.visits;
			
			parent = node.parent;
			for(Node n : node.children)
				children.add(n);
			
			moveInfo = node.moveInfo;
			
		}
		
		//---HELP ME--------------	
		//meant to be called through backprpagation to update weather the node that it has been visited and weather it won.
		public void updateNode(boolean didWin) {
			if(didWin)
				numWins++;
			visits++;
		}
		public int getvisits() {return visits;}
//		public String toString() {
//			return "" + id;
//		}
//		
		public void setParent(Node node) {
			parent = node;
		}
		public void addChild(Node node) {
			children.add(node);
		}
		public void RemoveChild(Node node) {
			children.remove(node);
		}
		public int childCount() {return children.size();}

	
}
