package com.egurnee.school.os.p2.sample;
import java.util.Random;

/**
 * @author Matt Evett
 * @version 1.0 Feb. 2013
 * @version 1.1 May 2015
 *
 */
public class Worker extends Thread {
	private static int MAX_SLEEP_MSEC = 1000;
	private static int MIN_SLEEP_MSEC = 100;
	private final boolean alterX; // Determines whether this worker is altering
									// X or Y coordinates
	private final int delta; // Amount by which data should be changed
	private final DataModel model;// The data model that this Worker alters
	private final Random rndGen; // Used to randomize sleep amounts

	public Worker(DataModel model, boolean isX, int howMuch) {
		this.alterX = isX;
		this.delta = howMuch;
		this.model = model;
		this.rndGen = new Random();
	}

	@Override
	public void run() {
		while (!this.model.isFinished()) { // (Alternatively, the thread could
											// maintain its own counter)
			try { // Sleep for a random interval
				Thread.sleep(MIN_SLEEP_MSEC
								+ this.rndGen.nextInt(MAX_SLEEP_MSEC));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (this.alterX) {
				this.model.updateX();
			} else {
				this.model.updateY();
			}

		}
	}
}
