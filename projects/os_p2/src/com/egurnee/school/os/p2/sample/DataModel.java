package com.egurnee.school.os.p2.sample;
/**
 *
 * @author matt
 * @version 1.0 Feb. 2013 This class represents the data underlying the
 *          animation. Always keep the GUI separate from the underlying data.
 *          The data, here, consists simply of the x-y coordinates of a point.
 *
 */
public class DataModel {
	private static final int MAX_UPDATES = 100; // After this many updates from
												// the workers, terminate
	private static int MAX_X = 20, MAX_Y = 20;
	private int posX = 0, posY = 0;
	private int totalUpdates = 0; // The total number of times the data has been
									// updated (i.e.,

	/**
	 * @return current value of X The get methods could be made synchronized,
	 *         but missing an update to the model would not affect the overall
	 *         performance particularly.
	 *
	 */
	public int getX() {
		return this.posX;
	}

	public int getY() {
		return this.posY;
	}

	/**
	 * Return true if the program should terminate. In particular, we want the
	 * animation to end after a fixed number of updates.
	 */
	public boolean isFinished() {
		return this.totalUpdates >= MAX_UPDATES;
	}

	public void reset() {
		this.posX = 0;
		this.posY = 0;
	}

	// the coordinates changed).
	/**
	 * @return new value of posX The update methods could be made synchronized,
	 *         but missing an update to the model would not affect the overall
	 *         performance particularly.
	 */
	public int updateX() {
		if (++this.posX >= MAX_X) {
			this.posX = 0; // wrap-around
		}
		this.totalUpdates++;
		return this.posX;
	}

	public int updateY() {
		if (++this.posY >= MAX_Y) {
			this.posY = 0; // wrap-around
		}
		this.totalUpdates++;
		return this.posY;
	}
}
