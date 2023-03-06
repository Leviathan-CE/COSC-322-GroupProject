package Search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import GameState.GameBoardState;

public class TreeSearch {
	private static ArrayList<node> visited = new ArrayList<>();
	private static node parent;
	private node[][] structure;

	// ---------CONSTRUCTORS--------------------
	// construct tree from adjacency list
	public TreeSearch(LinkedList<Integer> adjecencyList) {
		List<node> nodes = new ArrayList<>();
		for (Integer in : adjecencyList) {
			if (in > nodes.size()) {
				nodes.add(new node(in));
			}
		}

	}

	// Construct tree from non-adjacency matrix
	public TreeSearch(GameBoardState matrix) {
		structure = new node[matrix.getCurBoard().length][matrix.getCurBoard()[0].length];
		for (int x = 1; x < matrix.getCurBoard().length; x++) {
			for (int y = 1; x < matrix.getCurBoard()[0].length; y++) {
				structure[x][y] = new node();
			}
		}

	}

	private void connectEdges(int[][] matrix, int[] startLocation) {
		node root = structure[startLocation[0]][startLocation[1]]; 
		if( startLocation[0]==matrix.length && startLocation[1] == matrix[0].length) {
			return;
		}		

        if(startLocation[0] +1 < matrix.length) {
        	
        	
        }
        		
        if(startLocation[1] + 1 > matrix[0].length)
        	
        if(startLocation[0]-1 < 0)
        
       	if(startLocation[1]-1 < 0)
       		return;
        
	}

	// depth first tree traversal
	public static List<node> DFS(node root, node target) {
		if (root == null) {
			return null;
		}

		System.out.print(root.id + " ");
//		if (root.children.isEmpty()) {
//			if (parent != null)
//				visited.remove(parent);
//			parent = null;
//		}
		if (!root.children.isEmpty()) {
			parent = root;
			visited.add(root);
		}
//		if (root == target)
//			visited.add(target);

		for (node child : root.children) {
			DFS(child, target);
		}

		return visited;
	}

	public String toString() {
		String message = "[";
		for (node v : visited) {
			message += v.id + " ";
		}
		message += " ]";
		return message;
	}

	// tree node
	public static class node {
		int id;
		static int count = 0;
		public List<node> children;

		public node(int id) {
			this.id = id;
			count++;
			this.children = new ArrayList<>();
		}
		node(){
			id = count;
			count++;
			this.children = new ArrayList<>();
		}

		public String toString() {
			return "" + id;
		}

		public void addChild(node node) {
			children.add(node);
		}

	}

	public static void main(String[] args) {
		node root = new node(1);
		node node2 = new node(2);
		node node3 = new node(3);
		node node4 = new node(4);
		node node5 = new node(5);
		node node6 = new node(6);
		root.children.add(node2);
		root.children.add(node3);
		node2.children.add(node6);
		node3.children.add(node4);
		node3.children.add(node5);
		System.out.println(DFS(root, node4));
	}

}
