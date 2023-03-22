package monteCarlo;

import java.util.ArrayList;
import ActionFactory.ActionFactory;
import ActionFactory.Node;

public class mctsUpgraded {
	public static Node getMonteMove(Node root, int ourPlayer) { // ourPlayer : our queen colour (1 or 2)
		root.setPlayerNo(ourPlayer);
		double startTime = (System.currentTimeMillis() / 1000);	
		double currentTime = (System.currentTimeMillis() / 1000);
		double timeLimit = 10; // alloted time to make best choice
		while ((currentTime - startTime) < timeLimit) {
			// selection
			Node leaf = traverse(root);
			// expansion
			if(leaf.getVisits() != 1) { // only expand if you have already simulated the node
				expand(leaf);
				leaf = leaf.getChildren().get(0); 
			}
			// simulation
			simulate(leaf, ourPlayer);
			// backpropagate 
			backprop(leaf);
		}
		System.out.println("TIMES UP! ");
		return findBestChild(root);
	}
	
	//	traverses through tree using UCB, returns leaf
	public static Node traverse(Node root) {
		Node node = root;
		while(node.getChildren().size() > 0) {
			node = findBestChild(node);
		}
		return node;
	}
	
	//	returns parent's best child based on UCB
	public static Node findBestChild(Node parent) {
		ArrayList<Node> children = parent.getChildren();
		double largestUCB = 0;
		double tempUCB;
		Node bestChild = null;
		for (int c = 0; c < children.size(); c++) {	 //iterate through children and select child with highest UCB
			tempUCB = children.get(c).getUCB();
			if(tempUCB > largestUCB) {
				largestUCB = tempUCB;
				bestChild = children.get(c);
			}
		}
		return bestChild;
	}
	
	//	assigns all possible gamestates to a node as children
	public static void expand(Node parent) {
		int team = parent.getPlayerNo();
		ArrayList<Node> children = ActionFactory.getLegalMoves(parent, team);
		for (Node c : children) {
			c.setParent(parent);
			c.setPlayerNo(3 - team); // set children nodes as opponent team
		}
		parent.setChildren(children); // ADD : expand based on playerNo and assign opponentNo to children
	}
	
	// simulates random game from input node, look into possibly fixing up ?
	public static void simulate(Node node, int ourTeam) {
		int index;
		Node tempNode = node;
		int tempNodeTeam = node.getPlayerNo(); 
		ArrayList<Node> possibleMoves = ActionFactory.getLegalMoves(tempNode, tempNodeTeam);
		
		while(possibleMoves.size() > 0) {
            index = (int)(Math.random() * possibleMoves.size());
            tempNode = possibleMoves.get(index); 
            tempNode.setPlayerNo(3 - tempNodeTeam);
            tempNodeTeam = tempNode.getPlayerNo();
			possibleMoves = ActionFactory.getLegalMoves(tempNode, tempNodeTeam); 
		}
		if (tempNodeTeam == (3 - ourTeam)) {  // if their team have no moves left, we win
			node.incrWins(1);
		}
	}
	
	//	back propagates win value to all parents + updates visits for node and parents
	public static void backprop(Node node) {
		node.incrVisits();
		int simResult = node.getWins();	// 1 if game won, 0 if lost
		while(node.getParent() != null) {
			node = node.getParent();
			node.incrVisits();
			node.incrWins(simResult);
		}
	}
	
}
