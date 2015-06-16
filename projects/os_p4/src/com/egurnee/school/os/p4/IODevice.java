package com.egurnee.school.os.p4;

public class IODevice extends Thread {
	private final Job associatedJob;
	private final int numberOfMsecs;
	private final Scheduler scheduler;

	public IODevice(Job associatedJob, int numberOfMsecs, Scheduler scheduler) {
		this.associatedJob = associatedJob;
		this.numberOfMsecs = numberOfMsecs;
		this.scheduler = scheduler;
	}

	@Override
	public void run() {
		try {
			sleep(this.numberOfMsecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		this.scheduler.finishIO(this.associatedJob);
	}
}
