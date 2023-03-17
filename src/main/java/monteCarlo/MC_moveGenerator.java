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
	
	
	public Node mcts(Node root) {
		Node current = root;
		startTime = (System.currentTimeMillis() / 1000.);	// snatched from ejohn
		double currentTime = (System.currentTimeMillis() / 1000.);
		
		while ((currentTime - startTime) < timeLimit) {
			if(!current.getChildren().isEmpty()) { 	// not a leaf
				
			}
		}
	}
	

	
	public Node select(Node root) {
		Node bestChoice = null;
		return bestChoice;
	}
	public ArrayList<Node> expand(Node parent, int player) {
		ActionFactory actFact = new ActionFactory();
		ArrayList <Node>children = actFact.getLegalMoves(parent, player);
	}
	
}


