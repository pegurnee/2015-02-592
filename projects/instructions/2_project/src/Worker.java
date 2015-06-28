import java.util.Random;


/**
 * @author Matt Evett
 * @version 1.0 Feb. 2013
 * @version 1.1 May 2015
 *
 */
public class Worker extends Thread {
	private boolean alterX; // Determines whether this worker is altering X or Y coordinates
	private int delta;  	// Amount by which data should be changed
	private Random rndGen;	// Used to randomize sleep amounts
	private static int MAX_SLEEP_MSEC = 1000;
	private static int MIN_SLEEP_MSEC = 100;
	private DataModel model;// The data model that this Worker alters
	
	public Worker(DataModel model, boolean isX, int howMuch) {
		alterX = isX;
		delta = howMuch;
		this.model = model;
		rndGen = new Random();
	}
	
	public void run() {
		while(!model.isFinished()) {  // (Alternatively, the thread could maintain its own counter)
			try {  // Sleep for a random interval
				Thread.sleep(MIN_SLEEP_MSEC + rndGen.nextInt(MAX_SLEEP_MSEC));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (alterX)
				model.updateX();
			else // alter Y
				model.updateY();
			
		}
	}
}
