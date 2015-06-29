package os.proj.p3.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import os.proj.p3.core.FCFSScheduler;
import os.proj.p3.core.SystemSimulator;
import os.proj.p3.work.Submittor;
import os.proj.p3.work.WorkFactory;

/**
 * <p>Title: RunScheduler</p>
 * <p>Description: The driver class for the com.egurnee.school.os.p3 project.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Eastern Michigan University</p>
 * @author Matt Evett
 * @version 1.0
 * @author Clark Peters
 * @version 2.0
 */

/**
 * <p>
 * Title: RunScheduler
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015, 2004 by Matt Evett
 * </p>
 *
 * @author Matt Evett
 * @version 2.0 The main driver for the project.
 */

public class RunScheduler {

	private final static String INPUT_FILE_LOCATION = "assets/";
	private final static String INPUT_FILE_NAME = "scheduleInput.txt";

	// private final static String INPUT_FILE_NAME = "greek_input.txt";
	// private final static String INPUT_FILE_NAME = "titan_input.txt";

	/**
	 * Copies content of the input file into an array. Each line of input
	 * becomes one element of the array.
	 */
	public static void getJobsFromFile(ArrayList<String> listOfLines) {
		Scanner fileIn = null; // (Initialization keeps compiler happy)
		try { // open file
			fileIn = new Scanner(new FileInputStream(INPUT_FILE_LOCATION
					+ INPUT_FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out
			.println("Input file " + INPUT_FILE_NAME + " not found. ");
			System.exit(1);
		}
		while (fileIn.hasNextLine()) {
			// iterate through each file line
			listOfLines.add(fileIn.nextLine()); // Send line to ArrayList
		}
		fileIn.close(); // close file
	}

	public static void main(String[] args) {
		// set up for operating system simulator
		Thread thisThread = Thread.currentThread();
		thisThread.setPriority(Thread.MAX_PRIORITY);
		SystemSimulator kernel = new SystemSimulator(new FCFSScheduler());
		ArrayList<String> jobs = new ArrayList<String>();

		getJobsFromFile(jobs);

		WorkFactory sinecure = new WorkFactory();
		Submittor sub = new Submittor(jobs, kernel, sinecure);
		kernel.setPriority(8);
		kernel.start(); // Thread executing this instruction has higher
		// priority, so will continue to hold cpu
		sub.setPriority(7);
		sub.start();
		// As the driver exits, os has the highest priority, so should get the
		// cpu....
	}
}
