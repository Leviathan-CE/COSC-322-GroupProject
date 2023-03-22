package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

import ActionFactory.ActionFactory;
import ActionFactory.MonteTreeSearch;
import ActionFactory.Node;
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
		System.out.println("Who's Turn :"+QueenColor);

		// gen legal moves
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(root, QueenColor);
		CalcUtilityScore(chioces, root, QueenColor);
		if(chioces.size() == 0)
			throw new RuntimeException("WE LOOSE");
		Node chosenOne =  MonteTreeSearch.SearchMax(root);
		
		System.out.println("children in root: "+root.childCount());
		chosenOne.updateQueenPoses();

		//decoupleUnusedChildren(chosenOne, chioces, root);
		
		// get move package		
		int[] oldQ = chosenOne.moveInfo.getOldQPos();
		int[] newQ = chosenOne.moveInfo.getNewQPos();
		int[] arrw = chosenOne.moveInfo.getArrow();	
		

		System.out.println("chosenMove: Q: [" + oldQ[0] + ";" + oldQ[1] + "] nQ: [" + newQ[0] + ";" + newQ[1]
				+ "] arrw: [" + arrw[0] + ";" + arrw[1] + "]");
		chosenOne.printQPoses();
		
		chosenOne.print();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	protected static Node CalcUtilityScore(ArrayList<Node> chioces, Node root, int color) {
		// calculate the utility function for all the nodes that came from the root
		// temporary solution
		int count =0;
		for (Node n : chioces) {
			count++;
			int[] qs = n.countQueens();
			if(qs[0] != 4 || qs[1] !=4) {
				throw new IndexOutOfBoundsException("Queen count not accurate: Q1: "+qs[0]+"; Q2;"+qs[1]+" in iteration: "+count);
			}
			n.updateQueenPoses();
			n.C = Math.random() * 6;
			n.setGval(n.h1(color));
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
	protected static void decoupleUnusedChildren(Node chosenMove, ArrayList<Node> chioces, Node root) {
		chioces.remove(chosenMove);
		//only decouples nodes that are not part of the monte carlo tree
		for (Node n : chioces) {
			if (n.getvisits() > 1) {
				n.setParent(null);
				root.RemoveChild(n);
			}
		}
	}
	protected static void decoupleAllChildren(ArrayList<Node> chioces, Node root) {
				//only decouples nodes that are not part of the monte carlo tree
		for (Node n : chioces) {			
				root.RemoveChild(n);
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



}
