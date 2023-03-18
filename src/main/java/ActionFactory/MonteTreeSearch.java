package ActionFactory;

import java.util.ArrayList;

import Search.TreeSearch.node;

public class MonteTreeSearch {
	
	
	
	@SuppressWarnings("unused") //used only in search
	private static Node deepestChild;
	private static Node BestPick;
	
	/**
	 * EFFECTS: does a DFS Traversal of using tree style search. 
	 * MODIFES: This.
	 * @param root : the starting node to begin the search
 	 * @return :  the highest value non leaf node.
	 */
	public static Node Search(Node root) {		
		if(root == null) {		
			return null;			
		}	

		for(Node child : root.children) {
			child.setParent(root);
			deepestChild = child;
			if(BestPick == null)
				BestPick = child;
			if(BestPick.getValue() < child.getValue() && child.children.isEmpty())
				BestPick = child;
			Search(child);
		}
		
		return BestPick;
	}
	/**
	 * EFFECTS: Back propagates threw the tree and returns a list of the 
	 * nodes starting with the deepest one.
	 * @param node : deepest node to start back-propagating on.
	 * @return list of nodes starting from the deepest to the root.
	 */
	public static ArrayList<Node> getMoves(Node node){
		ArrayList<Node> moves = new ArrayList<>();
		while(node.parent != null) {
			moves.add(node);
			node = node.parent;
		}
		moves.add(node);
		return moves;
	}
	
	
	
//	public static void main(String[] args) {
//		Node root  = new Node(new int[11][11]);
//		Node node2 = new Node(new int[11][11]);
//		Node node3 = new Node(new int[11][11]);
//		Node node4 = new Node(new int[11][11]);
//		Node node5 = new Node(new int[11][11]);
//		Node node6 = new Node(new int[11][11]);
////		root.setUtilScore(9); //root value doesn't matter
////		node2.setUtilScore(2);
////		node3.setUtilScore(4);
////		node4.setUtilScore(3);
////		node5.setUtilScore(1);
////		node6.setUtilScore(3);
//		root.children.add(node2);
//		root.children.add(node3);
//		node2.children.add(node6);
//		node3.children.add(node4);
//		node3.children.add(node5);		
//		System.out.println(getMoves(Search(root)).toString());
//	}
}
