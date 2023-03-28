package Simulation;

import java.util.ArrayList;

import ActionFactory.ActionFactory;
import ActionFactory.MonteTreeSearch;
import ActionFactory.Node;
import Serailization.MonteTreeSerailizer;
import ubc.cosc322.MoveSequence;

public class SimBiulder {
	static int[][] gameboard = {{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, } };


	static Nueron Constants;
	static Node startPos = new Node(gameboard);

	public static void main(String[] args) {
		try {
		
			Constants = MonteTreeSerailizer.LoadNueron("constants.txt");
			System.out.println("stuff");
		}catch(Exception e) {
			System.out.println("file not found not being created yet");
			Constants = new Nueron(MoveSequence.C);
		}
		if(Constants == null)
			Constants = new Nueron(MoveSequence.C);
	
		if (MoveSequence.C.length != Constants.Wieghts.length)
			throw new RuntimeException("Constants and wieghts for sim are not same length");
		
		//apply constants
		MoveSequence.C = Constants.Wieghts;
		int games = 0; //games player
		int color = 1; //starting color
		Node cur = null; //current games state
		boolean gameEnd = false; //weather a game has ended
		while(games < 5) {
			
			//stating move
			System.out.println("games"+games);
			gameEnd = false;
			cur = playAturn(color, startPos);
			while(!gameEnd) {
				int colorC = color%2+1;
				try {
					//play a move setiching side everytime
					cur = playAturn(colorC,cur);
				}catch(Exception e) {
					//if win or loose apply a positive or negitive score
					//TODO: currently because it plays against a copy of it self
					//it always looses because it reads it self as both players instead of a single on.
					if(e.getMessage().contains("LOOSE") && colorC == 1) {						
						MoveSequence.C[getLargestWieght()] -= 1f;
					}
					if(e.getMessage().contains("WIN") && colorC == 2) {
						MoveSequence.C[getLargestWieght()] += 1f;
					}
					for(int i = 0; i < MoveSequence.C.length;i++) {
						System.out.print(MoveSequence.C[i]+" : ");
					}
					System.out.println();
					gameEnd = true;
				}
			}
			
			games++;
		}
		for(int i = 0; i < MoveSequence.C.length;i++) {
			System.out.print(MoveSequence.C[i]+" : ");
		}
		try {
			MonteTreeSerailizer.saveNueron(new Nueron(MoveSequence.C),"constants.txt");	
		}catch(Exception e) {
			System.out.println("something went wrong. possibly made changes to Nueron class");
			
		}
		
	}

	private static Node playAturn(int color, Node state) {
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(state, color, false);
		
		//if we loose
		if(chioces.size() == 0)
			throw new RuntimeException("LOOSE");
		ArrayList<Node> Echioces = ActionFactory.getLegalMoves(state, color, false);
		//if enen has no moves
		if(Echioces.size() == 0)
			throw new RuntimeException("Win");
		
		MoveSequence.CalcUtilityScore(chioces, state, color);
		return  MonteTreeSearch.SearchMax(state);
	}
	
	private static int getLargestWieght() {
		double largest= 0;
		int index =0;
		for(int i =0; i< MoveSequence.C.length;i++) {
			if(largest < MoveSequence.C[i]) {
				largest = MoveSequence.C[i];
				index = i;
			}
		}
		return index;
	}


}
