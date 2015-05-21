package com.egurnee.os.p1;


public abstract class AbstractWorkerThread extends Thread implements WorkerInterface {
	protected int[] numbers;
	protected WorkerType type;
	protected ResultItem result;
	
	public AbstractWorkerThread(int[] numbers, WorkerType type) {
		this.numbers = numbers;
		this.type = type;
	}
	
	@Override
	public void run() {
		System.out.println("Started " + this.type + " run() ");
		this.result = this.find();
		System.out.println("Ended " + this.type + " run() ");

		System.out.println(this.type + " result=[" + this.result + "]");
	}
	
	public ResultItem getResult() {
		if (this.result == null) {
			this.run();
		}
		return this.result;
	}
}
