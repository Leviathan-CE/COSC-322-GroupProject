package Exceptions;

public class GameLossException 	extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public GameLossException(String Message) {
			super("");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m -------"+Message+"-----");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m --------------------------");
			System.out.println("\u001B[31m --------------------------");
		}

}
