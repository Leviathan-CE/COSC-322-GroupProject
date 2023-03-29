package ActionFactory;

import java.util.ArrayList;

import GameState.Timer;
import ubc.cosc322.MoveSequence;

public class MiniMaxSearch extends MoveSequence {
	
	/**
	 * @EFFECTS : same method signiture as its partent 
	 * class. goes threw and genratate desired players moves 
	 * then for each of those move generated generates and evaluates 
	 * the miniax score which is is our best from the worst move and stores 
	 * that value in the parent node. 
	 * @param root : current baordstate passed by oppoent
	 * @param QueenColor : out team colour
	 * @return Node that has the best miniMax score
	 */
	public static Node MiniMax(Node root,int QueenColor) {
		
		System.out.println("------CHOSEN MOVE STATE--------");
		System.out.println("Method MINIMAX");
		System.out.println("Who's Turn :"+QueenColor);

		// gen legal moves
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(root, QueenColor, false);
		//CalcUtilityScore(chioces, root);
		if(chioces.size() == 0)
			throw new RuntimeException("!!!!!!!!!!!!!WE LOSE!!!!!!!!!!!");
		int enemyTeamColor = 0;
		if(QueenColor == 1)
			enemyTeamColor = 2;
		else 
			enemyTeamColor = 1;
		
		Node chosenOne = null;
		for( Node ourMove : chioces) {
			//if run out of time give 1 second to send it.
			if(Timer.currentTime() > 10)
				break;
			//expand the children
			ArrayList<Node> enemyChioce = ActionFactory.getLegalMoves(ourMove, enemyTeamColor, false); 
			CalcUtilityScore(chioces, root, QueenColor);
			
			//----hueristic 2 calc
			//parent.setGval(h2(enemyChioce,chioces));
			//pick our worst move from opponents chioces
			Node chosenMiniMax =  MonteTreeSearch.SearchMin(root);
			//set root child minimax value
			ourMove.miniMaxvVal = chosenMiniMax.getValue();
			//get best node to pick form oppenent worst
			if(chosenOne == null)
				chosenOne = ourMove;
			else if(chosenOne.miniMaxvVal+chosenOne.getValue() < ourMove.miniMaxvVal+chosenOne.getValue())
				chosenOne = ourMove;
			
			//decouple all enemy moves so they are not in the tree
			decoupleAllChildren(enemyChioce, ourMove);
		}
		for(Node child : chioces) {
			if(child.childCount() > 0)
				System.out.println("num children: "+child.childCount());
		}

		System.out.println("chosen move is null: " + (chosenOne == null));
		System.out.println("chosen move info is null: "+(chosenOne.moveInfo == null));
		decoupleUnusedChildren(chosenOne, chioces,root);
		
		System.out.println("children in root: "+root.childCount());
		//chosenOne.updateQueenPoses(); not needed

		//decoupleUnusedChildren(chosenOne, chioces, root);
		
		// get move package		
		int[] oldQ = chosenOne.moveInfo.getOldQPos();
		int[] newQ = chosenOne.moveInfo.getNewQPos();
		int[] arrw = chosenOne.moveInfo.getArrow();	
		

		System.out.println("chosenMove: Q: [" + oldQ[0] + ";" + oldQ[1] + "] nQ: [" + newQ[0] + ";" + newQ[1]
				+ "] arrw: [" + arrw[0] + ";" + arrw[1] + "]");
		chosenOne.printQPoses();
		
		chosenOne.print();
//		try { lso not needed as it take about 20 sec on average to calc first move
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("------END OF STATE--------");
		
		return chosenOne;
	}
	
	public static int h2( ArrayList<Node> enenmychioces, ArrayList<Node> ourchioces) {
		return enenmychioces.size() - ourchioces.size();
	}
}
