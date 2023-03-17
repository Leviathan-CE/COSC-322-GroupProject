package Serailization;

import java.util.ArrayList;
import java.util.HashSet;

import ActionFactory.Node;

/**
 * instance of a monte-carlo tree which will be saved and loaded to
 *	the idea is that if every gamestate can be transformed into a hashcode
 *	then gamestates that are the same will be overwritten with the new game data.
 *	while only ever storing every state the bot has picked ever with the values.
 */
public class MonteTree {
	
	//stores nodes if they have the same game state overrite the existing one
	private static HashSet<Node> MonteTree = new HashSet<>();
	
	//add any number of nodes to tree
	public static void add(ArrayList<Node> propagate) {
		propagate.stream().forEach((Node) -> {
			MonteTree.add(Node);
		});
	}

	public static void clearTree() {MonteTree.clear();}
	
	public ArrayList<Node> getTree(){
		ArrayList<Node> nodes = new ArrayList<>();
		MonteTree.stream().forEach((Node)->{
			nodes.add(Node);
		});
		return nodes;
	}
 }


