package os.proj.p1;

import os.proj.p1.workers.AbstractWorkerThread;
import os.proj.p1.workers.AverageWorkerThread;
import os.proj.p1.workers.MaximumWorkerThread;
import os.proj.p1.workers.MedianWorkerThread;
import os.proj.p1.workers.MinimumWorkerThread;
import os.proj.p1.workers.StandardDeviationWorkerThread;

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
