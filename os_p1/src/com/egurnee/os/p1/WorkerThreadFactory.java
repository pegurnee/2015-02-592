package com.egurnee.os.p1;

public class WorkerThreadFactory {

	public static AbstractWorkerThread createWorker(WorkerType workerType,
			int[] theNumbers) {
		AbstractWorkerThread worker = null;
		switch (workerType) {
			case AVERAGE:
				worker = new AverageWorkerThread(theNumbers);
				break;
			case MAXIMUM:
				worker = new MaximumWorkerThread(theNumbers);
				break;
			case MEDIAN:
				worker = new MedianWorkerThread(theNumbers);
				break;
			case MINIMUM:
				worker = new MinimumWorkerThread(theNumbers);
				break;
			case STANDARD_DEVIATION:
				worker = new StandardDeviationWorkerThread(theNumbers);
				break;
			default:
				break;
		}
		return worker;
	}

}
