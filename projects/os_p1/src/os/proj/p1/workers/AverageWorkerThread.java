package os.proj.p1.workers;

import os.proj.p1.WorkerType;
import os.proj.p1.results.ResultItem;

public class AverageWorkerThread extends AbstractWorkerThread {

	public AverageWorkerThread(int[] numbers) {
		super(numbers, WorkerType.AVERAGE);
	}

	@Override
	public ResultItem find() {
		double sum = this.numbers[0];
		for (int i = 1; i < this.numbers.length; i++) {
			sum += this.numbers[i];
		}
		return new ResultItem(this.type, sum / this.numbers.length);
	}

}
