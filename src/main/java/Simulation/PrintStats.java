package Simulation;

import Serailization.MonteTreeSerailizer;

/**
 * 
 * Prints saved weights from a file in data
 *
 */
public class PrintStats {
	
	
	public static void main(String[] args) {

	 //Nueron n =	MonteTreeSerailizer.LoadNueron("black_ctx_RL_01.txt");
	 Nueron n =	MonteTreeSerailizer.LoadNueron("constants.txt");

	 for (int i = 0; i< 7; i++) {
		 System.out.println(n.Wieghts[i]);
	 }
	}
}
