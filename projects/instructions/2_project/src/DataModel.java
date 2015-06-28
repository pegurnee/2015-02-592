/** 
 * 
 * @author matt
 * @version 1.0 Feb. 2013
 * This class represents the data underlying the animation.
 * Always keep the GUI separate from the underlying data.
 * The data, here, consists simply of the x-y coordinates of a point.
 *
 */
public class DataModel {
	private static final int MAX_UPDATES = 100; //After this many updates from the workers, terminate
	private static int  MAX_X = 20, 
						MAX_Y = 20;
	private int posX = 0, 
				posY = 0;
	private int totalUpdates = 0;   // The total number of times the data has been updated (i.e.,
									// the coordinates changed).  
	/**
	 * @return new value of posX
	 * The update methods could be made synchronized, but missing an update
	 * to the model would not affect the overall performance particularly.
	 */
	public int updateX() {
		if (++posX >= MAX_X)
			posX = 0;  // wrap-around
		totalUpdates++;
		return posX;
	}
	public int updateY() {
		if (++posY >= MAX_Y)
			posY = 0;  // wrap-around
		totalUpdates++;
		return posY;
	}
	/**
	 * @return current value of X
	 * The get methods could be made synchronized, but missing an update
	 * to the model would not affect the overall performance particularly.
	 * 
	 */
	public int getX() {
		return posX;
	}
	public int getY() {
		return posY;
	}
	public void reset() {
		posX = 0; posY = 0;		
	}
	/**
	 * Return true if the program should terminate.  In particular, we want the
	 * animation to end after a fixed number of updates.
	 */
	public boolean isFinished() {
		return totalUpdates >= MAX_UPDATES;
	}
}
