package ActionFactory;

public class MonteTreeSearch {

			//determine our team
			private int Team;
			
			//determine other team
			private int Opponent;
			
			//determine how long game is running for 
			private double startTime = (System.currentTimeMillis() / 1000.);
			
			private double maxTime = 100;
			
			
			
			
			

			///CONSTRUCTORRRR
	public MonteTreeSearch() {
		
		
		
		///CONSTRUCTORRRR
		
		
		
	}
	
	
	// More functions go here
	
	//including oour absolute favorite function - GIVE IT A ROOT AND LET IT COOK
	
	public int[] MonteCarlo(GameBoardState root) {
		System.out.println("MONTE CARLO ADVANCED ALGORITHM IS ROLLLLLING BABY LETS GOOOOOOOOOOOOOOOOO");
		
		//are we over our alloted time - lets figure that out 
		// maybe i need to do this here idk but its commentated out for now 
		//startTime = (System.currentTimeMillis() / 1000); /// THIS IS IN THE CONSTRUCTOR BOZO 
		
		// what time is it rn 
		double currentTime = (System.currentTimeMillis() / 1000.);
		
		//lot o work in this jambo below check it 
		while ((currentTime - startTime) < maxTime) {
			
			//traverse the root to get a new root called the leaf - this is important
			GameBoardState leaf = traverse(root);
			
			//ok now simulate the leaf node to get the all important simulation value 
			
			//to build 
			int sim_val = simulate(leaf);
			
			//levi built this what a goat
			backPropagate(leaf, sim_val);
			
			
			currentTime = (System.currentTimeMillis() / 1000.); //update the current time - its updating time baby 
	
		
		
		return null;
	}
	
	
	

}
