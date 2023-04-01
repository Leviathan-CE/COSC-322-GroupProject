package Util;

public class Timer {
	static long startTimer =0;
	
	public static void start() {
		Timer.startTimer = System.currentTimeMillis();
	}
	public static double currentTime() {
		return (double)(System.currentTimeMillis()-startTimer)/ 1000.;
		
	}
	
}
