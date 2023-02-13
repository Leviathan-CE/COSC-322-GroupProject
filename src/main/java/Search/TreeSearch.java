package Search;

import java.util.ArrayList;
import java.util.List;

public class TreeSearch {
	private static ArrayList<node> visited = new ArrayList<>();
	private static node parent;

	public static List<node> DFS(node root, node target) {
		if (root == null) {
			return null;
		}

		System.out.print(root.id + " ");
		if (root.children.isEmpty()) {
			if (parent != null)
				visited.remove(parent);
			parent = null;
		}
		if (!root.children.isEmpty()) {
			parent = root;
			visited.add(root);
		}
		if (root == target)
			visited.add(target);

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

	public static class node {
		int id;
		private List<node> children;
		private double f_value;
		private double g_value;

		node(int id) {
			this.id = id;
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
