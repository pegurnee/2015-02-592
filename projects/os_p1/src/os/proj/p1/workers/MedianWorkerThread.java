package os.proj.p1.workers;

import java.util.Arrays;

import os.proj.p1.WorkerType;
import os.proj.p1.results.ResultItem;

public class MedianWorkerThread extends AbstractWorkerThread {

	public MedianWorkerThread(int[] numbers) {
		super(numbers, WorkerType.MEDIAN);
	}

	@Override
	public ResultItem find() {
		int[] nums = Arrays.copyOf(this.numbers, this.numbers.length);
		Arrays.sort(nums);

		double median;
		if ((this.numbers.length % 2) == 0) {
			int middle = this.numbers.length / 2;
			median = (nums[middle - 1] + nums[middle]) / 2.0;
		} else {
			median = nums[(this.numbers.length - 1) / 2];
		}
		return new ResultItem(this.type, median);
	}

}
