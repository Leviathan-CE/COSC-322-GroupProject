package Exceptions;

public class GameWinException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GameWinException(String Message) {		
		super(Message);
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ----------------"+Message+"---------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");
		System.out.println("\\u001B[32m ------------------------------------------------");

	}

}
