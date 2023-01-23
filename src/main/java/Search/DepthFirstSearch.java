package Search;
import java.util.*;
/*
 * References:
 * https://www.simplilearn.com/tutorials/data-structure-tutorial/dfs-algorithm#:~:text=StackView%20Course-,What%20is%20a%20Depth%2DFirst%20Search%20Algorithm%3F,far%20as%20possible%20before%20backtracking.
 */
public class DepthFirstSearch {
	private Stack<Integer> stack;

	public DepthFirstSearch(int graphSize ) {
		stack = new Stack<Integer>();
		stack.setSize(graphSize);
	}
	public DepthFirstSearch(Collection<? extends Integer> GraphData) {
		stack = new  Stack<Integer>();
		stack.addAll(0, GraphData);
	}
	
	public void SelectSourceNode(int node) {
	int	srouce = stack.get(node);
	}
	
	class node{
		int[] connnections;
		
	}
}
