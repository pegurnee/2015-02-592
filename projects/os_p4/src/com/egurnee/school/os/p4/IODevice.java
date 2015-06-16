package com.egurnee.school.os.p4;

public class IODevice extends Thread {
	private Job associatedJob;
	private int numberOfMsecs;
	private Scheduler scheduler;

	@Override
	public void run() {
		try {
			sleep(this.numberOfMsecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
}
