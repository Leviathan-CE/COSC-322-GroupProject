package Simulation;

import java.util.ArrayList;
import java.util.Random;

import ActionFactory.ActionFactory;
import ActionFactory.MonteTreeSearch;
import GameState.Node;
import Serailization.MonteTreeSerailizer;
import ubc.cosc322.MoveSequence;
/**
 * SimBiulder is a class that simulates games of Amazons using A* 
 * were each team has the  same weights. 
 * @author Cryst
 *
 */
public class SimBiulder {
	static String fileName = "constants2.txt";
	static int[][] gameboard = { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, } };

	static Nueron Constants;
	static Node startPos = new Node(gameboard);

	public static void main(String[] args) {
		try {
			//attempt to load
			Constants = MonteTreeSerailizer.LoadNueron(fileName);
			System.out.println("stuff");
		} catch (Exception e) {
			//if not loaded make new from base constants
			System.out.println("file not found not being created yet");
			Constants = new Nueron(MoveSequence.C);
		}
		if (Constants == null)
			Constants = new Nueron(MoveSequence.C);

		if (MoveSequence.C.length != Constants.Wieghts.length)
			throw new RuntimeException("Constants and wieghts for sim are not same length");

		// apply constants
		MoveSequence.C = Constants.Wieghts;
		
		int games = 0; // games player
		int color = 1; // starting color
		Node cur = null; // current games state
		boolean gameEnd = false; // weather a game has ended
		while (games < 100) {

			// stating move
			System.out.println("games" + games);
			gameEnd = false;
			cur = playAturn(color, startPos);
			while (!gameEnd) {
				color = color % 2 + 1;
				try {
					// play a move setiching side everytime
					cur = playAturn(color, cur);
				} catch (Exception e) {
					// if win or loose apply a positive or negitive score
					// TODO: currently because it plays against a copy of it self
					// it always looses because it reads it self as both players instead of a single
					// on.
					System.out.println("color:" + color);
					//have 
					MoveSequence.C2 = MoveSequence.C;
					trianBlack(color,e);
						
					for (int i = 0; i < MoveSequence.C.length; i++) {
						System.out.print(MoveSequence.C[i] + " : ");
					}
					System.out.println();
					gameEnd = true;
				}
			}

			games++;
		}
		//print stats
		for (int i = 0; i < MoveSequence.C.length; i++) {
			System.out.print(MoveSequence.C[i] + " : ");
		}
		try { //save weights
			MonteTreeSerailizer.saveNueron(new Nueron(MoveSequence.C), fileName);
		} catch (Exception e) {
			System.out.println("something went wrong. possibly made changes to Nueron class");

		}

	}
	//apply reward when player has won or lost a match 
	private static void trianBlack(int color, Exception e) {
		if (color == 1) {
			if (e.getMessage().contains("LOOSE")) {
				// aaply small neg change to all
				for (int i = 0; i < MoveSequence.C.length; i++) {
					double v = Math.random()/2;
					MoveSequence.C[i] -= v;
					
				}

				MoveSequence.C[getLargestWieght()] -= .5f;
			}
			if (e.getMessage().contains("WIN")) {
				// apply small positive change to all
				for (int i = 0; i < MoveSequence.C.length; i++) {
					
					double v = Math.random()*2;
					MoveSequence.C[i] += v;
				}
				MoveSequence.C[getLargestWieght()] += .5f;
			}
		}
		else { // color = 2
			if (e.getMessage().contains("LOOSE")) {
				// if enemy loose add positive change
				for (int i = 0; i < MoveSequence.C.length; i++) {
					double v = Math.random()*2;
					MoveSequence.C[i] += v;
					
				}

				MoveSequence.C[getLargestWieght()] += .5f;
			}
			if (e.getMessage().contains("WIN")) {
				// if enemy wins apply small negative change to all
				for (int i = 0; i < MoveSequence.C.length; i++) {
					
					double v = Math.random()/2;
					MoveSequence.C[i] -= v;
				}
				MoveSequence.C[getLargestWieght()] -= .5f;
			}
		}
		for (int i = 0; i < MoveSequence.C.length; i++) {			
			if(MoveSequence.C[i] < 0)
				MoveSequence.C[i] = 0;
		}
	}
	
	/**
	 * Plays a single turn for a particular color using only A* hueristics 
	 * 
	 * @param color, player black = 1 white = 2
	 * @param state, current turn node
	 * @return
	 */
	private static Node playAturn(int color, Node state) {
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(state, color, false);

		// if we loose
		if (chioces.size() == 0)
			throw new RuntimeException("LOOSE");
		ArrayList<Node> Echioces = ActionFactory.getLegalMoves(state, color % 2 + 1, false);
		// if enen has no moves
		if (Echioces.size() == 0)
			throw new RuntimeException("WIN");
		
		MoveSequence.CalcUtilityScore(chioces, state, color);
		return MonteTreeSearch.SearchMax(state);
	}

	//returns the largest hueristic C onststant C the list
	private static int getLargestWieght() {
		double largest = 0;
		int index = 0;
		for (int i = 0; i < MoveSequence.C.length; i++) {
			if (largest < MoveSequence.C[i]) {
				largest = MoveSequence.C[i];
				index = i;
			}
		}
		return index;
	}

}
