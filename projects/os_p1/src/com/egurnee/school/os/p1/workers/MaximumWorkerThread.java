package com.egurnee.school.os.p1.workers;

import com.egurnee.school.os.p1.WorkerType;
import com.egurnee.school.os.p1.results.ResultItem;

public class MaximumWorkerThread extends AbstractWorkerThread {

	public MaximumWorkerThread(int[] numbers) {
		super(numbers, WorkerType.MAXIMUM);
	}

	@Override
	public ResultItem find() {
		int temp = this.numbers[0];
		for (int i = 1; i < this.numbers.length; i++) {
			if (this.numbers[i] > temp) {
				temp = this.numbers[i];
			}
		}
		return new ResultItem(this.type, temp);
	}

}
