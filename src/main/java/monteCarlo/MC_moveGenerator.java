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
	
	// selection, expansion, simulation, backpropagation

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
		
		while ((currentTime - startTime) < timeLimit) {
			current = root;
			while(current.isExpanded()) { 	// if not a leaf, traverse to best child
				bestChild = findBestChild(current);
				current = bestChild;
			}
			// for leaves :
			if (current.getChildren().size() > 1) { // if there remains more than one gamestate option
				if(current.getVisits() == 1) { 	// if node has not been visited, expand
					simulate(current, player);
					backprop(current);
				}
				else {
					expand(current, player);
					simulate(current.getChildren().get(0), player); // all children have infinite ucb, simulate first child 
				}
			}
			return current.getChildren().get(0); // if only one gamestate remains, return final state
		}
		return findBestChild(root); // when time runs out, return best option from provided gamestate/player
	}
	
	public ArrayList<Node> backprop(Node node) {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.add(node);
		int simResult = node.getWins();	// 1 if game won, 0 if lost
		while(node.getParent() != null) {
			node = node.getParent();
			nodeList.add(node);
			node.incrVisits();
			node.incrWins(simResult);
		}
		return nodeList;
	}
	
	// simulate a random game from current gamestate, update node wins (0 or 1) based on 
	// win/loss of sim, and increment visits
	public void simulate(Node node, int player) { 
		ArrayList<Node> possibleMoves = ActionFactory.getLegalMoves(node, player); 
		int index;
		Node newGameState;
		int temp_player = player;
		
		node.incrVisits();
		while(possibleMoves.size() > 0) {
            index = (int)(Math.random() * possibleMoves.size());
            newGameState = possibleMoves.get(index); 
            temp_player = (temp_player + 1) % 2;
            possibleMoves = ActionFactory.getLegalMoves(newGameState, player); 
		}
		if (temp_player == player) {  //if our player has no more moves left, we lost the simulation
			node.incrWins(1);
		}
	}
	public void expand(Node parent, int player) {
		parent.setChildren(ActionFactory.getLegalMoves(parent, player)); 
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