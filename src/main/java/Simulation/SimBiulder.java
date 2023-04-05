package Simulation;

import java.util.ArrayList;
import java.util.Random;

import ActionFactory.ActionFactory;
import GameState.Node;
import Search.DepthFirstSearch;
import Serailization.MonteTreeSerailizer;
import ubc.cosc322.MoveSequence;

/**
 * SimBiulder is a class that simulates games of Amazons using A*. the bot plays against itself in 
 * Evolutionary type manner being shocked with large amounts of randomness when it looses and take a penalty to its best stat,
 * and when it wins is rewarded With small amount of randomness and a boost to its best stat. 
 * with the idea we would find a local minimum and maximum of weights that would equalize between both bots 
 *  
 * @apiNote were other versions of this train such as always using the same weights for both
 * or inputing human bais into the starting stats instead of bots starting from 0 among different reward systems
 * overall we only managed to train it for about 500 times which was a fairly small sample size. and found that our 
 * best iteration was starting with human made weights and then letting the training maximize the values
 * 
 * 
 * 
 *
 */
public class SimBiulder {
	static String fileName = "ctx_RL_01.txt";
	static int[][] gameboard = { { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, { 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, } };

	static Nueron Constants_black;
	static Nueron Constants_white;
	static Node startPos = new Node(gameboard);

	public static void main(String[] args) {
		try {
			// attempt to load
			Constants_black = MonteTreeSerailizer.LoadNueron("black_" + fileName);
			Constants_white = MonteTreeSerailizer.LoadNueron("white_" + fileName);
			System.out.println("stuff");
		} catch (Exception e) {
			// if not loaded make new from base constants
			System.out.println("file not found not being created yet");
			Constants_black = new Nueron(MoveSequence.Cb);
			Constants_white = new Nueron(MoveSequence.Cw);
		}
		//if either objects are null set both
		if (Constants_black == null || Constants_white == null) {
			Constants_black = new Nueron(MoveSequence.Cb);
			Constants_white = new Nueron(MoveSequence.Cw);
			System.out.println("created new objects from move sequence");
		}

		if (MoveSequence.Cb.length != Constants_black.Wieghts.length ||
				MoveSequence.Cw.length != Constants_white.Wieghts.length)
			throw new RuntimeException("Constants and wieghts for sim are not same length");

		// apply constants		
	    //MoveSequence.Cb = new double[] {0,0,0,0,0,0,0};
	    //MoveSequence.Cw = new double[] {0,0,0,0,0,0,0};
		MoveSequence.Cb = Constants_black.Wieghts;
		MoveSequence.Cw = Constants_white.Wieghts;

		int games = 0; // games player
		int color = 1; // starting color
		Node cur = null; // current games state
		boolean gameEnd = false; // weather a game has ended
		while (games < 200) {
			color =1;
			// stating move
			System.out.println("game: " + games);
			gameEnd = false;
			cur = playAturn(color, startPos);
			while (!gameEnd) {
				color = color % 2 + 1;
				try {
					// play a move setiching side everytime
					cur = playAturn(color, cur);
				} catch (Exception e) {
					// if win or loose apply a positive or negitive score
					System.out.println(color+" tiggered");
					trianBlack(color, e);
					trianWhite(color, e);

					printStats();
					gameEnd = true;
				}
			}

			games++;
		}
		// print stats
		printStats();
		try { // save weights
			MonteTreeSerailizer.saveNueron(new Nueron(MoveSequence.Cb), "black_"+fileName);
			MonteTreeSerailizer.saveNueron(new Nueron(MoveSequence.Cw), "white_"+fileName);
		} catch (Exception e) {
			System.out.println("something went wrong. possibly made changes to Nueron class");

		}

	}
	private static void printStats() {
		for (int i = 0; i < MoveSequence.Cb.length; i++) {
			System.out.print(MoveSequence.Cb[i] + " : ");
		}
		System.out.println("black");
		for (int i = 0; i < MoveSequence.Cw.length; i++) {
			System.out.print(MoveSequence.Cw[i] + " : ");
		}
		System.out.println("white");
	}

	// apply reward when player has won or lost a match
	private static void trianBlack(int color, Exception e) {
		if (color == 1) {
			if (e.getMessage().contains("WIN")) {
				// apply small positive change to all
				for (int i = 0; i < MoveSequence.Cb.length; i++) {

					double v = Math.random() * 2;
					MoveSequence.Cb[i] += v;
				}
				MoveSequence.Cb[getLargestWieght()] += .5f;
				System.out.println("black won");
			}
		
		}
		if(color == 2) {
			if (e.getMessage().contains("WIN")) {
				// apply completely random change
				for (int i = 0; i < MoveSequence.Cb.length; i++) {
					double v = Math.random()*2;
					MoveSequence.Cb[i] += v;
					 v = Math.random()*2;
					MoveSequence.Cb[i] -= v;
				}

				MoveSequence.Cb[getLargestWieght()] -= .5f;
				System.out.println("black lost");
			}
		}
		
		for (int i = 0; i < MoveSequence.Cb.length; i++) {
			if (MoveSequence.Cb[i] < 0)
				MoveSequence.Cb[i] = 0;
		}
	}

	// apply reward when player has won or lost a match
	private static void trianWhite(int color, Exception e) {
		if (color == 2) {
			if (e.getMessage().contains("WIN")) {
				// apply small positive change to all
				for (int i = 0; i < MoveSequence.Cw.length; i++) {

					double v = Math.random() * 2;
					MoveSequence.Cw[i] += v;
				}
				MoveSequence.Cw[getLargestWieght()] += .5f;
				System.out.println("White won");
			}
		
		}if(color ==1) {
			if (e.getMessage().contains("WIN")) {
				// apply completely random change
				for (int i = 0; i < MoveSequence.Cw.length; i++) {
					double v = Math.random()*2;
					MoveSequence.Cw[i] += v;
					 v = Math.random()*2;
					MoveSequence.Cw[i] -= v;

				}

				MoveSequence.Cw[getLargestWieght()] -= .5f;
				System.out.println("White Lost");
			}
		}
		for (int i = 0; i < MoveSequence.Cw.length; i++) {
			if (MoveSequence.Cw[i] < 0)
				MoveSequence.Cw[i] = 0;
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
		return DepthFirstSearch.SearchMax(state);
	}

	// returns the largest hueristic C onststant C the list
	private static int getLargestWieght() {
		double largest = 0;
		int index = 0;
		for (int i = 0; i < MoveSequence.Cb.length; i++) {
			if (largest < MoveSequence.Cb[i]) {
				largest = MoveSequence.Cb[i];
				index = i;
			}
		}
		return index;
	}

}
