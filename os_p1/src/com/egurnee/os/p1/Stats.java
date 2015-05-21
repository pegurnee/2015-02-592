package com.egurnee.os.p1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Stats {
	static int[] nums1 = { 1, 2, 3, 3, 5, 34, 6 };
	static int[] nums2 = { 1, 6, 6, 5, 3, 2, 25, 630 };
	static int[] nums3 = { 90, 81, 78, 95, 79, 72, 85, 6, 8 };
	static int[] nums4 = { 1, 2, 3 };

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
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
			for (Integer i : incoming) {
				System.out.println("i: " + i);
			}
		}

		int[] items = incoming.stream().mapToInt(i -> i).toArray();
		// new Stats(items).runAll();
		new Stats(nums2).runAll();

		keyboard.close();
	}

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
	}

	public void runAll() {
		for (AbstractWorkerThread worker : this.workers) {
			worker.start();
		}

		for (Integer i : this.theNumbers) {
			System.out.println("i: " + i);
		}
	}

}
