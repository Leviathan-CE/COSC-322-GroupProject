package monteCarlo;

import java.util.ArrayList;
import ActionFactory.ActionFactory;
import GameState.Node;
import GameState.Timer;
import ubc.cosc322.MoveSequence;
/**
 * Monte Carlo Algorithm 
 * 
 *
 */
public class mctsUpgraded {
	public static Node getMonteMove(Node root, int ourPlayer) { // ourPlayer : our queen colour (1 or 2)
		System.out.println("generating monte move ");
		root.setPlayerNo(ourPlayer);
		expand(root); 
		double startTime;
		double timeLimit = 28;
		startTime = (System.currentTimeMillis() / 1000);
		
		while ((System.currentTimeMillis() / 1000 - startTime) < timeLimit) {
			// selection
			Node leaf = traverse(root);
			// expansion
			if(leaf.getVisits() != 1) { // only expand if you have already simulated the node
				expand(leaf);
				if (leaf.getChildren().size() > 0) {
					leaf = leaf.getChildren().get(0);   // !!!FIX FOR ENDGAME CONDITION "Index 0 out of bounds for length 0"
				}
			}
			// simulation
			simulate(leaf, ourPlayer);
			// backpropagate 
			backprop(leaf);
		}
		System.out.println("TIMES UP! ");
		for (Node c : root.getChildren()) {
			System.out.printf("UCB: %.6f" + ", wins: " + c.getWins() + ", visits: " + c.getVisits() + "\n", c.getUCB() );
			}
		System.out.println("root visits: " + root.getVisits() );
		Node chosen = findBestChildWUtil(root, ourPlayer);
		MoveSequence.decoupleAllChildren(root);
		return chosen;
	}
	
	//	traverses through tree using UCB, returns leaf
	private static Node traverse(Node root) {
		Node node = root;
		while(node.getChildren().size() > 0) {
			node = findBestChild(node);
			if (node == null) {System.out.println("node is null");}
		}
		return node;
	}
	
	//	returns parent's best child based on UCB
//	private static Node findBestChild(Node parent) {
//		ArrayList<Node> children = parent.getChildren();
//		
//		double largestUCB = -1;
//		double tempUCB;
//		Node bestChild = null;
//		for (Node c : children) {	 //iterate through children and select child with highest UCB
//			tempUCB = c.getUCB();
//			if(tempUCB > largestUCB) {
//				largestUCB = tempUCB;
//				bestChild = c;
//			}
//		}
//		return bestChild;
//	}	
	//	returns parent's best child based on UCB
	private static Node findBestChild(Node parent) {
		ArrayList<Node> children = parent.getChildren();
	
		Node chosen = null;
		for( Node n : children) {
			if(chosen == null)
				chosen = n;
			if(chosen.getUCB() < n.getUCB())
				chosen = n;
		}
		return chosen;
	}
	/**
	 * Find best child with H+UBC
	 * @param parent
	 * @param color
	 * @return best node
	 */
	private static Node findBestChildWUtil(Node parent, int color) {
		ArrayList<Node> children = parent.getChildren();
		MoveSequence.CalcUtilityScore(children, parent, color);
		Node chosen = null;
		for( Node n : children) {
			if(chosen == null)
				chosen = n;
			if(chosen.getValue() < n.getValue())
				chosen = n;
		}
		
		//MoveSequence.decoupleUnusedChildren(bestChild, children,parent);
		return chosen;
	}	
	//	assigns all possible gamestates to a node as children
	private static void expand(Node parent) {
		int team = parent.getPlayerNo();
		ArrayList<Node> children = ActionFactory.getLegalMoves(parent, team, true);
		for (Node c : children) {
			c.setParent(parent);
			c.setPlayerNo(3 - team); // set children nodes as opponent team
		}
		parent.setChildren(children); 
	}
	
	// simulates random game from input node, look into possibly fixing up ?
	private static void simulate(Node node, int ourTeam) {
		int index;
		Node tempNode = node;
		int tempNodeTeam = node.getPlayerNo(); 
		ArrayList<Node> possibleMoves = ActionFactory.getLegalMoves(tempNode, tempNodeTeam, true);
		
		while(possibleMoves.size() > 0) {
            index = (int)(Math.random() * possibleMoves.size());
            tempNode = possibleMoves.get(index); 
            tempNode.setPlayerNo(3 - tempNodeTeam);
            tempNodeTeam = tempNode.getPlayerNo();
			possibleMoves = ActionFactory.getLegalMoves(tempNode, tempNodeTeam, true); 
		}
		if (tempNodeTeam == (3 - ourTeam)) {  // if their team have no moves left, we win
			node.incrWins(1);
		}
	}
	
	//	back propagates win value to all parents + updates visits for node and parents
	private static void backprop(Node node) {
		node.incrVisits();
		int simResult = node.getWins();	// 1 if game won, 0 if lost
		while(node.getParent() != null) {
			node = node.getParent();
			node.incrVisits();
			node.incrWins(simResult);
		}
	}
	private static ArrayList<Node> getchildrenUCB(Node parent) {
		ArrayList<Node> children = parent.getChildren();
		for (Node c : children) {	 //iterate through children and select child with highest UCB
			c.setUCB();
		}
		return children;
	}
	
}
