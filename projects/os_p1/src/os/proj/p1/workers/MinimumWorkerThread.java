package os.proj.p1.workers;

import os.proj.p1.WorkerType;
import os.proj.p1.results.ResultItem;

public class MinimumWorkerThread extends AbstractWorkerThread {

	public MinimumWorkerThread(int[] numbers) {
		super(numbers, WorkerType.MINIMUM);
	}

	@Override
	public ResultItem find() {
		int temp = this.numbers[0];
		for (int i = 1; i < this.numbers.length; i++) {
			if (this.numbers[i] < temp) {
				temp = this.numbers[i];
			}
		}
		return new ResultItem(this.type, temp);
	}

}
