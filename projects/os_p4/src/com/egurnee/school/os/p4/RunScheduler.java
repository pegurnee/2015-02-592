package com.egurnee.school.os.p4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
 * @author eddie
 * @version 2.5
 */

public class RunScheduler {

	private final static String INPUT_FILE_NAME = "scheduleInput.txt";

	// private final static String INPUT_FILE_NAME = "assets/greek_input.txt";
	// private final static String INPUT_FILE_NAME = "assets/titan_input.txt";

	/**
	 * Copies content of the input file into an array. Each line of input
	 * becomes one element of the array.
	 */
	public static void getJobsFromFile(ArrayList<String> listOfLines) {
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new FileInputStream(INPUT_FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out.println("Input file " + INPUT_FILE_NAME + " not"
					+ " found. ");
			System.exit(1);
		}
		while (fileIn.hasNextLine()) {
			listOfLines.add(fileIn.nextLine());
		}
		fileIn.close();
	}

	public static void main(String[] args) {
		Thread thisThread = Thread.currentThread();
		thisThread.setPriority(Thread.MAX_PRIORITY);
		SystemSimulator kernel = new SystemSimulator(new FCFSScheduler());
		ArrayList<String> jobs = new ArrayList<String>();

		getJobsFromFile(jobs);

		WorkFactory sinecure = new WorkFactory();
		Submittor sub = new Submittor(jobs, kernel, sinecure);
		kernel.setPriority(8);
		kernel.start();
		sub.setPriority(7);
		sub.start();
	}
}
