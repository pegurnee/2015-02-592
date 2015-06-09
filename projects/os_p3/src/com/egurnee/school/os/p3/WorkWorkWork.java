package com.egurnee.school.os.p3;

public class WorkWorkWork implements JobWorkable {

	@Override
	public void doWork() {
		final int limit = 5;
		for (int j = 0; j < limit; j++) {
			System.out.println("I'm on thread: " + Thread.currentThread()
								+ " printing: " + j);
		}
	}

}
