package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;

import ActionFactory.ActionFactory;
import Exceptions.GameLossException;
import Exceptions.GameWinException;
import GameState.Node;
import MiniMax.MiniMaxSearch;
import Search.DepthFirstSearch;
import monteCarlo.mctsUpgraded;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
/**
 * 
 * 
 * How We calculate a move based on the different methods implemented
 *
 */
public class MoveSequence {
	//{1f,10,5f,.5f,.35,.5f} v1
	//{10,50,1,.5f,20,.5f} v2
	private static int curturn;
	/**
	 * Weight Constants for heuristics for black team
	 */
	public static double[] Cb = new double[] {10,1,50,.5f,20,.5f,10} ;
	/**
	 * Weight Constants for heuristics for white team
	 */
	public static double[] Cw = new double[] {10,1,50,.5f,20,.5f,10};

	/**
	 * EFFECTS : generates all possible legal actions from action factory and then
	 *          calculates the utility score for each. then chooses the maxuim from
	 *          the deepest node using a depth first search and extracts which move
	 *          was made and returns a sender object for the game client.
	 * 
	 * @param root       : the game state Node that the game is currently in
	 * @param QueenColor : which Queen/Team are we generating moves for
	 * 
	 * @return Node that is chsen and filled with move infomation
	 */
	public static Node GenerateMove(Node root, int QueenColor, int turn) {
		curturn = turn;
		System.out.println("------CHOSEN MOVE STATE--------");
		System.out.println("Who's Turn :" + QueenColor);
		System.out.println("TURN NUM: "+ turn);

		// gen legal moves
		Node chosenOne = null;
		ArrayList<Node> chioces= null;
		if(turn < 0) {
			chosenOne = MiniMaxSearch.MiniMax(root, QueenColor);
			chioces = ActionFactory.getLegalMoves(root, QueenColor, true);
		}
		if (turn > 12) {			
			chosenOne =  mctsUpgraded.getMonteMove(root, QueenColor);	
			chioces = ActionFactory.getLegalMoves(root, QueenColor, true);
			
		}else {
			chioces = ActionFactory.getLegalMoves(root, QueenColor, false);
			CalcUtilityScore(chioces, root, QueenColor); 
			chosenOne = DepthFirstSearch.SearchMax(root);
		}
		
		
		System.out.println("is null " +chosenOne !=null);
		//win loose conditions
		if(chioces.size() == 0)
			throw new GameLossException("WE LOOSE");	
		ArrayList<Node> Echioces = ActionFactory.getLegalMoves(root, QueenColor % 2 + 1, true);
		// if enen has no moves
		if (Echioces.size() == 0)
			throw new GameWinException("WIN");
		
		
		System.out.println("children in root: " + root.childCount());
		chosenOne.updateQueenPoses();

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
	 * EFFECTS : is the package sender. that sends packages to the server
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
	 * EFFFECTS : from the root applies heuristics and Monte-cCrlo through the Node
	 * @param chioces : all the children of the root
	 * @param root    : the original state and Parent
	 */
	 public static Node CalcUtilityScore(ArrayList<Node> chioces, Node root, int color) {
		// calculate the utility function for all the nodes that came from the root
		// temporary solution
		int count = 0;
		for (Node n : chioces) {
			count++;
			int[] qs = n.countQueens();
			if (qs[0] != 4 || qs[1] != 4) {
				throw new IndexOutOfBoundsException(
						"Queen count not accurate: Q1: " + qs[0] + "; Q2;" + qs[1] + " in iteration: " + count);

			}
			n.updateQueenPoses();
			calcUtil(n,color);
			//System.out.println("h1: "+n.getH1() +" h2: "+n.getH2()+" h3: "+n.getH3()+ " h4: "+n.getH4()+" h5: "+n.getH5());

		}
		return root;
	}
	 /**
	  * Calculates a single Nodes utility score for a particular team
	  * @param n : target node to calculate
	  * @param color : color of team 
	  * @return utility score
	  */
	 public static double calcUtil(Node n, int color){
		 if(color ==1) {	
		 n.setH1(n.H1(color)* Cb[0]); //v1:
			n.setH2(n.H2(color)* Cb[1]);// v1:
			n.setH3(n.H3(color, Cb[2])); //v1: 			
			n.setH4(n.H4()*Cb[3]);	//v1:
			n.setH5(n.H5(color)*Cb[4]); //v1:
			n.setUCB(n.getUCB() * Cb[6]);
			if(curturn >20)
				n.setH6(n.H6() * Cb[5]);
			n.setUCB(n.getValueUCB() * Cw[6]);
			}
			else if(color ==2) {
				n.setH1(n.H1(color)* Cw[0]); //v1:
				n.setH2(n.H2(color)* Cw[1]);// v1:
				n.setH3(n.H3(color, Cw[2])); //v1: 				
				n.setH4(n.H4()*Cw[3]);		//v1:
				n.setH5(n.H5(color)*Cw[4]); //v1:
			
				if(curturn >20)
					n.setH6(n.H6() * Cw[5]);
				n.setUCB(n.getValueUCB() * Cw[6]);
			}
		 return n.GetUtilityVal();
	 }

	/**
	 * EFFECTS : decouples all choices from root that were not the chosen node to
	 *          make a move on. to prevent potential memory leaks.
	 * @param chosenMove : move picked to send to client
	 * @param chioces    : all the children of root
	 * @param root       : the initial game state node
	 */
	public static void decoupleUnusedChildren(Node chosenMove, ArrayList<Node> chioces, Node root) {

		chioces.remove(chosenMove);
		// only decouples nodes that are not part of the monte carlo tree
		for (Node n : chioces) {
			if (n.getVisits() > 0) {
				n.setParent(null);
				root.RemoveChild(n);
			}
		}
	}
	/**
	 * Removes all children from parent node 
	 * @param root : parent node to children want to be removed
	 */
	public static void decoupleAllChildren(Node root) {
		// decouple all children including choice		
			root.setChildren(null);
		

	}
	/**
	 * Transforms our data that contains a move into a format that server can read
	 * @param oldQ : old queen position
	 * @param newQ : the move made
	 * @param arrw : new arrow position
	 * @return list a of lists that server can read.
	 */
	public static ArrayList<ArrayList<Integer>> setSenderObj(int[] oldQ, int[] newQ, int[] arrw) {

		ArrayList<ArrayList<Integer>> SenderOBJ = new ArrayList<>();
		ArrayList<Integer> oldquen = new ArrayList<Integer>(Arrays.asList(oldQ[0] + 1, oldQ[1] + 1));
		ArrayList<Integer> newquen = new ArrayList<Integer>(Arrays.asList(newQ[0] + 1, newQ[1] + 1));
		ArrayList<Integer> arrowMe = new ArrayList<Integer>(Arrays.asList(arrw[0] + 1, arrw[1] + 1));
		SenderOBJ.add(oldquen);
		SenderOBJ.add(newquen);
		SenderOBJ.add(arrowMe);
		return SenderOBJ;
	}

}
