package com.egurnee.os.p1;

public class StandardDeviationWorkerThread extends AbstractWorkerThread {

	public StandardDeviationWorkerThread(int[] numbers) {
		super(numbers, WorkerType.STANDARD_DEVIATION);
	}

	@Override
	public ResultItem find() {
		double sum = this.numbers[0];
		for (int i = 1; i < this.numbers.length; i++) {
			sum += this.numbers[i];
		}
		double mean = sum / this.numbers.length;

		double sumOfSquares = 0;
		for (int i = 0; i < this.numbers.length; i++) {
			sumOfSquares += Math.pow(this.numbers[i] - mean, 2);
		}
		double variance = sumOfSquares / this.numbers.length;

		return new ResultItem(this.type, Math.sqrt(variance));
	}

}
