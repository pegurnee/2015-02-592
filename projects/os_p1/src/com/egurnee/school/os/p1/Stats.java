package com.egurnee.school.os.p1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.egurnee.school.os.p1.results.ResultItem;
import com.egurnee.school.os.p1.results.ResultsSingleton;
import com.egurnee.school.os.p1.workers.AbstractWorkerThread;

public class Stats {
	static int[] nums1 = { 1, 2, 3, 3, 5, 34, 6 };
	static int[] nums2 = { 1, 6, 6, 5, 3, 2, 25, 630 };
	static int[] nums3 = { 90, 81, 78, 95, 79, 72, 85, 6, 8 };
	static int[] nums4 = { 1, 2, 3 };

	public static void main(String[] args) throws InterruptedException {
		Scanner keyboard = new Scanner(System.in);
		ArrayList<Integer> incoming = new ArrayList<>();

		System.out.println("Enter a series of numbers"
				+ ", anything else to cancel.");
		try {
			while (true) {
				System.out.print("Enter a number: ");
				incoming.add(keyboard.nextInt());
			}
		} catch (InputMismatchException ex) {
		}

		int[] items = incoming.stream().mapToInt(i -> i).toArray();
		final Stats stats = new Stats(items);
		// final Stats stats = new Stats(nums3);

		stats.runAll();

		System.out.println("\nStatistics of the list:");
		final ResultItem[] results = stats.getResults();
		for (int j = 0; j < results.length; j++) {
			System.out.println("    | " + results[j]);
		}

		keyboard.close();
	}

	private boolean started;

	private final int[] theNumbers;
	private final AbstractWorkerThread[] workers;

	public Stats(int[] theNumbers) {
		this.theNumbers = theNumbers;

		final WorkerType[] enums = WorkerType.values();
		this.workers = new AbstractWorkerThread[enums.length];

		for (int i = 0; i < this.workers.length; i++) {
			this.workers[i] = WorkerThreadFactory.createWorker(enums[i],
					this.theNumbers);
		}

		this.started = false;
	}

	public ResultItem[] getResults() {
		if (!this.started) {
			this.runAll();

			return null;
		}

		try {
			for (AbstractWorkerThread worker : this.workers) {
				worker.join();
			}

			final WorkerType[] enums = WorkerType.values();
			ResultItem[] toReturn = new ResultItem[enums.length];

			for (int i = 0; i < toReturn.length; i++) {
				toReturn[i] = ResultsSingleton.getInstance().get(enums[i]);
			}

			return toReturn;
		} catch (InterruptedException e) {
			return null;
		}
	}

	public void runAll() {
		this.started = true;
		for (AbstractWorkerThread worker : this.workers) {
			worker.start();
		}
	}
}
