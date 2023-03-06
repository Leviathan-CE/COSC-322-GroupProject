package ActionFactory;

import java.util.ArrayList;

import Search.TreeSearch.node;

public class MonteTreeSearch {
	
	int teamOpponent;
	int teamUs;
	static ArrayList<Node> visited = new ArrayList<>();
	static Node parent;
	static Node deepestChild;
	
	
	public static Node Search(Node root, Node Parent) {		
		if(root == null) {		
			return null;			
		}
		System.out.println(root.id + " ");

		for(Node child : root.children) {
			child.setParent(root);
			deepestChild = child;
			Search(child, child);
		}
		//System.out.println(deepestChild.toString());
		return deepestChild;
	}
	public static ArrayList<Node> getMoves(Node node){
		ArrayList<Node> moves = new ArrayList<>();
		while(node.parent != null) {
			moves.add(node);
			node = node.parent;
		}
		return moves;
	}
	
	public static void main(String[] args) {
		Node root  = new Node(new int[11][11]);
		Node node2 = new Node(new int[11][11]);
		Node node3 = new Node(new int[11][11]);
		Node node4 = new Node(new int[11][11]);
		Node node5 = new Node(new int[11][11]);
		Node node6 = new Node(new int[11][11]);
		root.children.add(node2);
		root.children.add(node3);
		node2.children.add(node6);
		node3.children.add(node4);
		node3.children.add(node5);		
		System.out.println(getMoves(Search(root, null)).toString());
	}
}
