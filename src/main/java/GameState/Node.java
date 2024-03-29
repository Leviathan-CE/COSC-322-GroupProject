package GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Node Representation for the game tree
 *
 */
public class Node extends GameBoardState {

	int id = 1;
	static int count = 1;

	private double miniMaxVal = -1000000000;

	private double M = 0; // monte-carlo value

	private double h1 = -1; // hueristic vals
	private double h2 = -1;
	private double h3 = -1;
	private double h4 = -1;
	private double h5 = -1;
	private double h6 = -1;
	/**
	 * Carlo constant
	 */
	public double C = .5; // carlo constant

	private int wins = 0;
	private int visits = 0;
	private boolean expanded = false;
	private double ucb = 0;
	private int playerNo;

	/**
	 * parent node reference
	 */
	Node parent;
	/**
	 * this Nodes children
	 */
	ArrayList<Node> children = new ArrayList<>();

	/**
	 * calculation UBC value for Monte Carlo
	 * 
	 * @return UCB vlaue
	 */
	public double getUCB() {
		if (this.visits == 0) {
			return Integer.MAX_VALUE;
		}
		return (this.wins / (double) this.visits) + C * Math.sqrt(Math.log(this.parent.visits) / this.visits);
	}

	/**
	 * calculate UBC Value for Ultility function
	 * 
	 * @return UCB or 0 for utility score
	 */
	public double getValueUCB() {
		if (this.visits == 0) {
			return 0;
		}
		return (this.wins / (double) this.visits) + C * Math.sqrt(Math.log(this.parent.visits) / this.visits);
	}
	
	public void setUCB(double n) {
		this.ucb = n;
	}

	public void setUCB() {
		this.ucb = getUCB();
	}

	public void setMiniMax(double val) {
		miniMaxVal = val;
	}

	public double getMiniMax() {
		return miniMaxVal;
	}

	/**
	 *  get the evaluation of the game board
	 * @return utility score
	 */
	public double GetUtilityVal() {
		return (h1 + h2 + h3 + h4 + h5 + h6 + ucb);
	}

	public double getH1() {
		return h1;
	}

	public double getH2() {
		return h2;
	}

	public double getH3() {
		return h3;
	}

	public double getH4() {
		return h4;
	}

	public double getH5() {
		return h5;
	}

	public double getH6() {
		return h6;
	}

	public void setH1(double h1) {
		this.h1 = h1;
	}

	public void setH2(double h2) {
		this.h2 = h2;
	}

	public void setH3(double h3) {
		this.h3 = h3;
	}

	public void setH4(double h4) {
		this.h4 = h4;
	}

	public void setH5(double h5) {
		this.h5 = h5;
	}

	public void setH6(double h6) {
		this.h6 = h6;
	}

	// -----CONSTRUCTORS-----------
	
	public Node(ArrayList<Integer> gameBoard) {
		super(gameBoard);
		id = count++;
	}

	public Node(int[][] gameBoard) {
		super(gameBoard);
		id = count++;
	}
	
	public Node(int[][] gameBoard, Node parent) {
		super(gameBoard);
		this.setParent(parent);
		id = count++;
	}

	/**
	 * deep copy a node
	 * 
	 * @param node
	 */
	public Node(Node node) {
		super(node.getCurBoard());

		M = node.M;
		h1 = node.h1;
		h2 = node.h2;
		h3 = node.h3;
		h4 = node.h4;
		h5 = node.h5;
		h6 = node.h6;
		ucb = node.ucb;
		miniMaxVal = node.miniMaxVal;
		C = node.C;
		id = node.id;
		wins = node.wins;
		visits = node.visits;

		parent = node.parent;
		for (Node n : node.children)
			children.add(n);

		moveInfo = node.moveInfo;

	}

	// ---HELP ME--------------
	public void setParent(Node node) {
		parent = node;
	}

	public Node getParent() {
		return this.parent;
	}

	public void addChild(Node node) {
		children.add(node);
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public ArrayList<Node> getChildren() {
		return this.children;
	}

	public boolean isExpanded() {
		return this.expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public int getVisits() {
		return this.visits;
	}

	public void incrVisits() {
		this.visits++;
	}

	public int getWins() {
		return this.wins;
	}

	public void incrWins(int simResult) {
		this.wins = this.wins + simResult;
	}

	public void RemoveChild(Node node) {
		children.remove(node);
	}

	public int childCount() {
		if (children != null)
			return children.size();
		return -1;
	}

	public int getPlayerNo() {
		return this.playerNo;
	}

	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}

}
