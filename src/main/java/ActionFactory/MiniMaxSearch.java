package ActionFactory;

import java.util.ArrayList;

import GameState.Timer;
import ubc.cosc322.MoveSequence;

public class MiniMaxSearch extends MoveSequence {

	public static Node MiniMax(Node root,int QueenColor) {
		
		System.out.println("------CHOSEN MOVE STATE--------");
		System.out.println("Method MINIMAX");
		System.out.println("Who's Turn :"+QueenColor);

		// gen legal moves
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(root, QueenColor);
		CalcUtilityScore(chioces, root);
		if(chioces.size() == 0)
			throw new RuntimeException("!!!!!!!!!!!!!WE LOOSE!!!!!!!!!!!");
		int enemyTeamColor = 0;
		if(QueenColor == 1)
			enemyTeamColor = 2;
		else 
			enemyTeamColor = 1;
		
		Node chosenOne = null;
		for( Node child : chioces) {
			if(Timer.currentTime() > 29)
				break;
			//expand the children
			ArrayList<Node> enemyChioce = ActionFactory.getLegalMoves(child, enemyTeamColor); 
			CalcUtilityScore(chioces, root);
			//pick our worst move from opponents chioces
			Node chosenMiniMax =  MonteTreeSearch.SearchMin(root);
			//set root child minimax value
			child.miniMaxvVal = chosenMiniMax.getValue();
			//get best node to pick form oppenent worst
			if(chosenOne == null)
				chosenOne = child;
			else if(chosenOne.miniMaxvVal < child.miniMaxvVal)
				chosenOne = child;
			
			//decouple all enemy moves so they are not in the tree
			decoupleAllChildren(enemyChioce, child);
		}
		for(Node child : chioces) {
			if(child.childCount() > 0)
				System.out.println("num children: "+child.childCount());
		}

		System.out.println("chosen move is null: " + (chosenOne == null));
		System.out.println("chosen move info is null: "+(chosenOne.moveInfo == null));
		decoupleUnusedChildren(chosenOne, chioces,root);
		
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
}
