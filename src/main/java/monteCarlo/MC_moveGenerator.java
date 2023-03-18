package monteCarlo;
import java.util.ArrayList;

import ActionFactory.ActionFactory;
import ActionFactory.Node;
import GameState.GameBoardState;

// selection, expansion, simulation, backpropagation
public class MC_moveGenerator {
	private double timeLimit = 25;
	private int myTeam;
	private double startTime;
	
	// constructor
	public MC_moveGenerator(int myTeam) {
		this.myTeam = myTeam;	}
	
	// while time remains:
	// 1. is current a leaf? 
	// 		- no? choose child with lowest ucb, return to 1
	//	 	- yes? has current been visited before? (n != 0?)
	//			- yes? expand current (add all possible gamestates)
	//				current = first new child node, SIMULATE, backpropagate
	//			- no? SIMULATE! backpropagate
	
	
	public Node mcts(Node root, int player) {
		Node current = null;
		Node bestChild = null;
		startTime = (System.currentTimeMillis() / 1000.);	// snatched from ejohn, implement our timer instead?
		double currentTime = (System.currentTimeMillis() / 1000.);
		int simResult;	// result of random simulation, 0 for loss, 1 for win

		while ((currentTime - startTime) < timeLimit) {
			current = root;
			while(current.isExpanded()) { 	// not a leaf, traverse to best child
				bestChild = findBestChild(current);
				current = bestChild;
			}
			// for leaves :
			if (current.getChildren().size() > 1) { // if there remains more than one gamestate option
				if(current.getVisits() == 0) { 	// if node has not been visited, expand
					simResult = simulate(current);
					backprop(current, simResult);
				}
				else {
					expand(current, player);
					simResult = simulate(current.getChildren().get(0)); // all children have infinite ucb, simulate first child 
				}
			}
			return current.getChildren().get(0); // if only one gamestate remains, return final state
		}
		return findBestChild(root); // when time runs out, return best option from provided gamestate/player
	}
	public void backprop(Node node, int simResult) {
		int gameWon = simResult;	// 1 if game won, 0 if lost
		node.inc
		while(node.getParent() != null) {
			moves.add(node);
			node = node.parent;
		}
		moves.add(node);
		return moves;
	}
	public int simulate(Node node) {
		return 1;
	}
	public void expand(Node parent, int player) {
		ActionFactory actFact = new ActionFactory();
		parent.setChildren(actFact.getLegalMoves(parent, player)); 
		parent.setExpanded(true);
	}
	public Node findBestChild(Node parent) {
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
}


