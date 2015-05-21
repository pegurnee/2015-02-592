package com.egurnee.os.p1;

public abstract class AbstractWorkerThread extends Thread implements
WorkerInterface {
	protected int[] numbers;
	protected ResultItem result;
	protected WorkerType type;

	public AbstractWorkerThread(int[] numbers, WorkerType type) {
		this.numbers = numbers;
		this.type = type;
	}

	public ResultItem getResult() {
		if (this.result == null) {
			this.run();
		}
		return this.result;
	}

	@Override
	public void run() {
		this.result = this.find();

		ResultsSingleton.getInstance().put(this.type, this.result);
	}
}
