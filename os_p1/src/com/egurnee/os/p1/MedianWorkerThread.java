package com.egurnee.os.p1;

import java.util.Arrays;

public class MedianWorkerThread extends AbstractWorkerThread {

	public MedianWorkerThread(int[] numbers) {
		super(numbers, WorkerType.MEDIAN);
	}

	@Override
	public ResultItem find() {
		// TODO Auto-generated method stub
		int[] nums = Arrays.copyOf(this.numbers, this.numbers.length);
		Arrays.sort(nums);
		
		double median = -1;
		if (this.numbers.length % 2 == 0) {
			int middle = this.numbers.length / 2;
			median = (nums[middle] + nums[middle + 1]) / 2.0;
		} else {
			median = nums[(this.numbers.length + 1) / 2];
		}
		
		return new ResultItem(this.type, median);
	}

}
