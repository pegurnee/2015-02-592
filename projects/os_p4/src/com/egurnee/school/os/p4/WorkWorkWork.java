package com.egurnee.school.os.p4;

public class WorkWorkWork implements JobWorkable {
	private int jobNumber;

	@Override
	public void doWork() {
		System.out.println("I'm on thread: " + Thread.currentThread()
							+ " printing: " + this.jobNumber++);
	}

}
