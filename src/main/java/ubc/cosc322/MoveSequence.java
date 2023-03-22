package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

import ActionFactory.ActionFactory;
import ActionFactory.MonteTreeSearch;
import ActionFactory.Node;
import monteCarlo.mcts;
import monteCarlo.mctsUpgraded;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;

public class MoveSequence {



	/**
	 * @EFFECTS : generates all possible legal actions from action factory and then
	 *          calculates the utility score for each. then chooses the maxuim from
	 *          the deepest node using a depth first search and extracts which move
	 *          was made and returns a sender object for the game client.
	 * 
	 * @param root       : the game state Node that the game is currently in
	 * @param QueenColor : which Queen/Team are we generating moves for
	 * 
	 * @return  Node that is chsen and filled with move infomation
	 */
	public static Node GenerateMove(Node root, int QueenColor) {
		System.out.println("------CHOSEN MOVE STATE--------");
		// gen legal moves
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(root, QueenColor);
		CalcUtilityScore(chioces, root);
		
		Node chosenOne =  mctsUpgraded.getMonteMove(root, QueenColor);
//		Node chosenOne =  mctsUpgraded.getMonteMove(root, QueenColor);

		
		System.out.println("children in root: "+root.childCount());
		chosenOne.updateQueenPoses();

	//	decoupleUnusedChildren(chosenOne, chioces, root);
		
		// get move package		
		int[] oldQ = chosenOne.moveInfo.getOldQPos();
		int[] newQ = chosenOne.moveInfo.getNewQPos();
		int[] arrw = chosenOne.moveInfo.getArrow();	
		

		System.out.println("chosenMove: Q: [" + oldQ[0] + ";" + oldQ[1] + "] nQ: [" + newQ[0] + ";" + newQ[1]
				+ "] arrw: [" + arrw[0] + ";" + arrw[1] + "]");
		chosenOne.printQPoses();
		
		chosenOne.print();
		System.out.println("------END OF STATE--------");

		return chosenOne;
	}

	/**
	 * @EFFECTS : is the package sender. that sends packages to the server
	 * 
	 * @param ui             : is the GameBaseGUI reference to update the client GUI
	 * @param client         : is the server Package reference we want to ship to
	 * @param contentPackage : the content being the old queen position new queen
	 *                       position and arrow in that order to be sent to GUI and
	 *                       client server.
	 */
	public static void sendPackageToServer(BaseGameGUI ui, GameClient client,
			ArrayList<ArrayList<Integer>> contentPackage) {
		client.sendMoveMessage(contentPackage.get(0), contentPackage.get(1), contentPackage.get(2));
		ui.updateGameState(contentPackage.get(0), contentPackage.get(1), contentPackage.get(2));
	}

	/**
	 * @EFFFECTS : from the root applies heuristics and Monte-cCrlo through the Node
	 * @param chioces : all the children of the root
	 * @param root    : the original state and Parent
	 */
	private static Node CalcUtilityScore(ArrayList<Node> chioces, Node root) {
		// calculate the utility function for all the nodes that came from the root
		// temporary solution
		int count =0;
		for (Node n : chioces) {
			count++;
			int[] qs = n.countQueens();
			if(qs[0] != 4 || qs[1] !=4) {
	//		throw new IndexOutOfBoundsException("Queen count not accurate: Q1: "+qs[0]+";"+qs[1]+" in iteration: "+count);
			}
			n.updateQueenPoses();
			n.C = Math.random() * 5;
			//n.setGval(n.h1());
		}
		return root;
	}

	/**
	 * @EFFECTS : decouples all choices from root that were not the chosen node to
	 *          make a move on. to prevent potential memory leaks.
	 * @param chosenMove : move picked to send to client
	 * @param chioces    : all the children of root
	 * @param root       : the initial game state node
	 */
	public static void decoupleUnusedChildren(Node chosenMove, ArrayList<Node> chioces, Node root) {
		chioces.remove(chosenMove);
		//only decouples nodes that are not part of the monte carlo tree
		for (Node n : chioces) {
			if (n.getvisits() > 1) {
				n.setParent(null);
				root.RemoveChild(n);
			}
		}
	}

	public static ArrayList<ArrayList<Integer>> setSenderObj(int[] oldQ, int[] newQ, int[] arrw) {

		ArrayList<ArrayList<Integer>> SenderOBJ = new ArrayList<>();
		ArrayList<Integer> oldquen = new ArrayList<Integer>(Arrays.asList(oldQ[0]+1, oldQ[1]+1));
		ArrayList<Integer> newquen = new ArrayList<Integer>(Arrays.asList(newQ[0]+1, newQ[1]+1));
		ArrayList<Integer> arrowMe = new ArrayList<Integer>(Arrays.asList(arrw[0]+1, arrw[1]+1));
		SenderOBJ.add(oldquen);
		SenderOBJ.add(newquen);
		SenderOBJ.add(arrowMe);
		return SenderOBJ;
	}

//	// get queen position that changed between two states looking for the initial
//	// state
//	private static int[] getOldQueenPos(Node root, Node chosenMove, int QueenColor) {
//		int[] oldQuen = new int[2];
//		for (int i = 0; i < 4; i++) {
//			// find oldqueen pos for black
//			if (QueenColor == 1)
//				if (root.getQueenPosition1().get(i)[0] != chosenMove.getQueenPosition1().get(i)[0]
//						|| root.getQueenPosition1().get(i)[1] != chosenMove.getQueenPosition1().get(i)[1]) {
//					oldQuen = root.getQueenPosition1().get(i);
//					break;
//				}
//			// get old pos if white
//			if (QueenColor == 2)
//				if (root.getQueenPosition2().get(i)[0] != chosenMove.getQueenPosition2().get(i)[0]
//						|| root.getQueenPosition2().get(i)[1] != chosenMove.getQueenPosition2().get(i)[1]) {
//					oldQuen = root.getQueenPosition2().get(i);
//					break;
//				}
//		}
//		System.out.println("oldQinMeth: " + oldQuen[0] + ";" + oldQuen[1]);
//		return oldQuen;
//	}
//
//	// get queen position that changed between two states looking for the new
//	// position.
//	private static int[] getNewQueenPos(Node root, Node chosenMove, int QueenColor) {
//		int[] newQuen = new int[2];
//		for (int i = 0; i < 4; i++) {
//			// find oldqueen pos for black
//			if (QueenColor == 1)
//				if (root.getQueenPosition1().get(i)[0] != chosenMove.getQueenPosition1().get(i)[0]
//						|| root.getQueenPosition1().get(i)[1] != chosenMove.getQueenPosition1().get(i)[1]) {
//					newQuen = chosenMove.getQueenPosition1().get(i);
//					break;
//				}
//			// get old pos if white
//			if (QueenColor == 2)
//				if (root.getQueenPosition2().get(i)[0] != chosenMove.getQueenPosition2().get(i)[0]
//						|| root.getQueenPosition2().get(i)[1] != chosenMove.getQueenPosition2().get(i)[1]) {
//					newQuen = chosenMove.getQueenPosition2().get(i);
//					break;
//				}
//		}
//		System.out.println("NewQinMeth: " + newQuen[0] + ";" + newQuen[1]);
//		return newQuen;
//	}
//
//	// looking for the new arrow that was thrown
//	private static int[] getArrow(Node root, Node chosenMove) {
//		int[] arrow = new int[2];
//		// get arrow from new game state
//		for (int x = 0; x < chosenMove.getArrowPositions().size(); x++) {
//			// if our newest arrow is last in list grab it return it
//			if (x == chosenMove.getArrowPositions().size() - 1) {
//				arrow = chosenMove.getArrowPositions().get(x);
//				break;
//			}
//			
//			// otherwise loop through until we find the arrow pair thats different
//			if (chosenMove.getArrowPositions().get(x)[0] != root.getArrowPositions().get(x)[0]||
//				chosenMove.getArrowPositions().get(x)[1] != root.getArrowPositions().get(x)[1]) {
//					arrow = chosenMove.getArrowPositions().get(x);
//					break;
//				
//			}
//		}
//		return arrow;
//	}

}
