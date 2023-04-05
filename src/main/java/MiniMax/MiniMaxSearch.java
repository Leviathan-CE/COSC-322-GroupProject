package MiniMax;

import java.util.ArrayList;

import ActionFactory.ActionFactory;
import GameState.Node;
import Search.DepthFirstSearch;
import Simulation.Nueron;
import Util.Timer;
import ubc.cosc322.MoveSequence;
/**
 * 
 * MiniMax search Algorithm with alpha beta pruning, using a iterative depth first search.
 *
 */
public class MiniMaxSearch {

	private static int nodesVisited = 0;
	private static int moveVisited = 0;
	private static int depthlimit = 5;
	private static int leafVisited = 0;
	private static double timeLimit = 28;

	/**
	 *		    EEFECTS: Generated desired players moves then for each of those move
	 *          generated generates and evaluates the miniax score which is is our
	 *          best from the worst move and stores that value in the parent node.
	 * @param root       : current baord state
	 * @param QueenColor : out team colour
	 * @return Node that has the best miniMax score
	 */
	public static Node MiniMax(Node root, int QueenColor) {

		nodesVisited = 0;
		moveVisited = 0;
		leafVisited = 0;
		System.out.println("Generating MINIMAX");
		System.out.println("Who's Turn :" + QueenColor);

		// gen legal our moves
		ArrayList<Node> chioces = ActionFactory.getLegalMoves(root, QueenColor, false);
		MoveSequence.CalcUtilityScore(chioces, root, QueenColor);

		int enemyTeamColor = QueenColor % 2 + 1;
		Node chosenOne = null;

		for (Node ourMove : chioces) {
			moveVisited++;
			if (chosenOne == null)
				chosenOne = ourMove;
			// if run out of time give 1 second to send it.
			if (Timer.currentTime() > timeLimit)
				break;
			
			//calc best move by depth first search and alpha beta pruning
			ourMove.setMiniMax(minValue(ourMove, QueenColor, enemyTeamColor, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			if (ourMove.getMiniMax() > chosenOne.getMiniMax())
				chosenOne = ourMove;
		}

		System.out.println("nodesVisited=" + nodesVisited);
		System.out.println("Moves Vistited=" + moveVisited);
		System.out.println("Leafs Vistied=" + leafVisited);
		return chosenOne;
	}

	/**
	 * Maximize our value for our move
	 * 
	 * @param move      : current action taken
	 * @param ourTeam   : black or white
	 * @param EnemyTeam : blakc or white
	 * @param a         : alpha
	 * @param b         : beta
	 * @param depth     : current depth of the tree
	 * @return utility score
	 */
	private static double maxValue(Node move, int ourTeam, int EnemyTeam, double a, double b, int depth) {
		nodesVisited++;
		depth++;
		
		ArrayList<Node> Chioces = ActionFactory.getLegalMoves(move, ourTeam, true);
		// when max depth reached return score
		if (depth > depthlimit)
			depth = depthlimit;
		if (depth == depthlimit || Chioces.size() == 0) {
			leafVisited++;
			return MoveSequence.calcUtil(move, ourTeam);
		}

		double max = Integer.MIN_VALUE;
		// alpha beta pruning
		for (Node n : Chioces) {
			if (Timer.currentTime() > timeLimit)
				break;
			Node temp = new Node(n);
			max = Math.max(max, minValue(temp, ourTeam, EnemyTeam, a, b, depth));
			if (max >= b)
				return max;
			a = Math.max(a, max);
		}
		return max;
	}

	/**
	 * minimize our opponents best move
	 * 
	 * @param move      : current action taken in tree
	 * @param ourTeam   : color
	 * @param EnemyTeam : color
	 * @param a         : alpha
	 * @param b         : beta
	 * @param depth     : current depth of the tree
	 * @return utility score
	 */
	private static double minValue(Node move, int ourTeam, int EnemyTeam, double a, double b, int depth) {
		nodesVisited++;
		depth++;

		ArrayList<Node> Chioces = ActionFactory.getLegalMoves(move, EnemyTeam, true);
		// when max depth reached return score
		if (depth > depthlimit)
			depth = depthlimit;
		if (depth == depthlimit || Chioces.size() == 0) {
			leafVisited++;
			return MoveSequence.calcUtil(move, EnemyTeam);
		}

		double min = Integer.MAX_VALUE;
		// alpha beta pruning
		for (Node n : Chioces) {
			if (Timer.currentTime() > timeLimit)
				break;
			Node temp = new Node(n);
			min = Math.min(min, maxValue(temp, ourTeam, EnemyTeam, a, b, depth));
			if (min <= a)
				return min;
			b = Math.min(b, min);
		}
		return min;
	}

}
