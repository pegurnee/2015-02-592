package com.egurnee.school.os.p4.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.egurnee.school.os.p4.core.FCFSScheduler;
import com.egurnee.school.os.p4.core.SystemSimulator;
import com.egurnee.school.os.p4.work.Submittor;
import com.egurnee.school.os.p4.work.WorkFactory;

public class RunScheduler {

	private final static String INPUT_FILE_NAME = "scheduleInput.txt";

	// private final static String INPUT_FILE_NAME = "assets/greek_input.txt";
	// private final static String INPUT_FILE_NAME = "assets/titan_input.txt";

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
