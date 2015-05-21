package com.egurnee.os.p1;

public class Stats {
	static int[] nums = { 90, 81, 78, 95, 79, 72, 85, 6, 8 };

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//System.out.println(new MinimumWorkerThread(nums).find());

		new Stats(nums).runAll();
		

	}

	private final int[] theNumbers;

	private final AbstractWorkerThread[] workers;

	public Stats(int[] theNumbers) {
		this.theNumbers = theNumbers;

		final WorkerType[] enums = WorkerType.values();
		this.workers = new AbstractWorkerThread[enums.length];

		for (int i = 0; i < this.workers.length; i++) {
			this.workers[i] = WorkerThreadFactory.createWorker(enums[i],
					theNumbers);
		}
	}

	public void runAll() {
		for (AbstractWorkerThread worker : this.workers) {
			worker.start();
		}
	}

}
