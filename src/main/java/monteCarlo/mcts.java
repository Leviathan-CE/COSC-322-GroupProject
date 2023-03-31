package monteCarlo;
import java.util.ArrayList;

import ActionFactory.ActionFactory;
import GameState.Node;
import ubc.cosc322.MoveSequence;

// selection, expansion, simulation, backpropagation
public class mcts {
	
			
	// selection, expansion, simulation, backpropagation

	// while time remains:
	// 1. is current a leaf? 
	// 		- no? choose child with lowest ucb, return to 1
	//	 	- yes? has current been visited before? (n != 0?)
	//			- yes? expand current (add all possible gamestates)
	//				current = first new child node, SIMULATE, backpropagate
	//			- no? SIMULATE! backpropagate
	
//	public static Node getMonteMove(Node root, int player) { // player : our queen colour (1 or 2)
//		Node current = null;
//		Node bestChild = null;
//		double startTime;
//		double timeLimit = 10;
//		startTime = (System.currentTimeMillis() / 1000);	// snatched from ejohn, implement our timer instead?
//		double currentTime = (System.currentTimeMillis() / 1000);
//		System.out.println("timer started");
//		while ((currentTime - startTime) < timeLimit) {
//			System.out.println("while loop start");
//			current = root;
//			while(current.isExpanded()) { 	// if not a leaf, traverse to best child
//				System.out.println("traversing to a leaf...");
//				bestChild = findBestChild(current);
//				current = bestChild;
//			}
//			// for leaves :
//			if(current.getVisits() == 1) { 	// if node has not been visited, simulate
//				simulate(current, player);
//				System.out.println("simulation 1 ran, sim value: " + current.getWins());
//				backprop(current);
//				System.out.println("result backpropagated");
//			}
//			else { // if node has been visited, expand then simulate
//				expand(current);
//				simulate(current.getChildren().get(0), player); // all children have infinite ucb, simulate first child 
//				System.out.println("simulation 2 ran, sim value: " + current.getWins());
//				backprop(current); 
//				System.out.println("result backpropagated");
//				}
//			currentTime = (System.currentTimeMillis() / 1000);
//			}
//		System.out.println("TIMES UP! ");
//		bestChild = findBestChild(root);
//		MoveSequence.decoupleUnusedChildren(bestChild, root.getChildren(), root);
//		return bestChild; // when time runs out, return best option from provided gamestate/player
//		
//	}
//	
//	public static void backprop(Node node) {
//		int simResult = node.getWins();	// 1 if game won, 0 if lost
//		while(node.getParent() != null) {
//			node = node.getParent();
//			node.incrVisits();
//			node.incrWins(simResult);
//		}
//	}
//	
//	// simulate a random game from current gamestate, update node wins (0 or 1) based on 
//	// win/loss of sim, and increment visits
//	public static void simulate(Node node, int player) { // player : our queen colour (1 or 2)
//		System.out.println("simulating.....");
//		ArrayList<Node> possibleMoves = ActionFactory.getLegalMoves(node, player); 
//		int index;
//		Node newGameState;
//		int temp_player = player;
//		node.incrVisits();
//		while(possibleMoves.size() > 0) {
//            index = (int)(Math.random() * possibleMoves.size());
//            newGameState = possibleMoves.get(index); 
//            temp_player = (temp_player + 1) % 2;
//            possibleMoves = ActionFactory.getLegalMoves(newGameState, player); 
//		}
//		if (temp_player == player) {  //if our player has no more moves left, we lost the simulation
//			node.incrWins(1);
//		}
//	}
//	public static void expand(Node parent) {
//		int team = parent.getPlayerNo();
//		parent.setChildren(ActionFactory.getLegalMoves(parent, team)); // ADD : expand based on playerNo and assign opponentNo to children
//		parent.setExpanded(true);
//		System.out.println("expanded!");
//	}
//	public static Node findBestChild(Node parent) {
//		ArrayList<Node> children = parent.getChildren();
//		double largestUCB = 0;
//		double tempUCB;
//		Node bestChild = null;
//		for (int c = 0; c < children.size(); c++) {	 //iterate through children and select child with highest UCB
//			tempUCB = children.get(c).getUCB();
//			if(tempUCB > largestUCB) {
//				largestUCB = tempUCB;
//				bestChild = children.get(c);
//			}
//		}
//		return bestChild;
//	}
}