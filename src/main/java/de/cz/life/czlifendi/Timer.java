package de.cz.life.czlifendi;

public class Timer {

	public static void sleepForXMilliseconds(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			throw new TimerException("Oops!", e);
		}
	}

	public static void runForXSeconds(int seconds, Runnable runnable) {
		long startTime = System.currentTimeMillis();
		while (!timesUp(seconds, startTime)) {
			runnable.run();
		}
	}

	private static boolean timesUp(int secondsToRun, long startTime) {
		int millisecondsToRun = 1000 * secondsToRun;
		long passedMilliseconds = System.currentTimeMillis() - startTime;
		return passedMilliseconds > millisecondsToRun;
	}

}
