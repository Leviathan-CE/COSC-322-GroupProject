package Util;

/**
 * 
 * Timer class to keep track of time in seconds
 *
 */
public class Timer {
	static long startTimer =0;
	
	/**
	 * starts the timer
	 */
	public static void start() {
		Timer.startTimer = System.currentTimeMillis();
	}
	/**
	 * 
	 * @return returns amount of time insecond since start was called
	 */
	public static double currentTime() {
		return (double)(System.currentTimeMillis()-startTimer)/ 1000.;
		
	}
	
}
