package Simulation;

import Serailization.MonteTreeSerailizer;

public class PrintStats {

	public static void main(String[] args) {
		
	 Nueron n =	MonteTreeSerailizer.LoadNueron("sim_start_0.txt");
	 for (int i = 0; i< 7; i++) {
		 System.out.println(n.Wieghts[i]);
	 }
	}
}
