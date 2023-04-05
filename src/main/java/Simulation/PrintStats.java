package Simulation;

import Serailization.MonteTreeSerailizer;

public class PrintStats {

	public static void main(String[] args) {
		
	 Nueron n =	MonteTreeSerailizer.LoadNueron("black_ctx_RL_01.txt");
	 for (int i = 0; i< 7; i++) {
		 System.out.println(n.Wieghts[i]);
	 }
	}
}
