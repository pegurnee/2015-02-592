package os.proj.p4.core;

import os.proj.p4.work.Job;

public class IODevice extends Thread {
	private final Job associatedJob;
	private final SystemSimulator kernel;
	private final int numberOfMsecs;
	private final Scheduler scheduler;

	public IODevice(Job associatedJob, int numberOfMsecs, Scheduler scheduler,
			SystemSimulator kernel) {
		this.associatedJob = associatedJob;
		this.numberOfMsecs = numberOfMsecs;
		this.scheduler = scheduler;
		this.kernel = kernel;
	}

	@Override
	public void run() {
		try {
			sleep(this.numberOfMsecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.scheduler.finishIO(this.associatedJob);
	}
}
