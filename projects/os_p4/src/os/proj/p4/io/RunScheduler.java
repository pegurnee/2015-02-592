package os.proj.p4.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import os.proj.p4.core.FCFSScheduler;
import os.proj.p4.core.SystemSimulator;
import os.proj.p4.work.Submittor;
import os.proj.p4.work.WorkFactory;

public class RunScheduler {

	private final static String INPUT_FILE_LOCATION = "assets/";
	private final static String INPUT_FILE_NAME = "scheduleInput.txt";

	// private final static String INPUT_FILE_NAME = "greek_input.txt";
	// private final static String INPUT_FILE_NAME = "titan_input.txt";

	public static void getJobsFromFile(ArrayList<String> listOfLines) {
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new FileInputStream(INPUT_FILE_LOCATION
														+ INPUT_FILE_NAME));
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
		System.gc();

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
