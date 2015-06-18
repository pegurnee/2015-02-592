package com.egurnee.school.os.p4.core;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.egurnee.school.os.p4.work.Job;

public class FCFSScheduler extends Scheduler {
	private final ConcurrentLinkedQueue<Job> theInputQueue = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Job> theReadyQueue = new ConcurrentLinkedQueue<>();

	@Override
	public synchronized void add(Job J) {
		System.out.println(Thread.currentThread()
				+ " adding process to ready queue.");
		this.notify();
		this.theReadyQueue.add(J);
	}

	@Override
	public synchronized void blockTilThereIsAJob() {
		if (this.hasRunningJob()) {
			return;
		}

		while (!this.hasJobsQueued()) {
			System.out.println(Thread.currentThread()
					+ " is blocking until there is a job.");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("evidently there is now a job on readyQ");
	}

	@Override
	public synchronized void finishIO(Job j) {
		this.theInputQueue.remove(j);
		this.add(j);
		this.clearRunningJob();
		// this.notify();

		// j.getMyCondition().signal();
		// this.theReadyQueue.add(j);
		// j.shouldRun();
	}

	@Override
	public boolean hasJobsQueued() {
		return (this.hasReadyJobs() || (null != this.theInputQueue.peek()));
	}

	@Override
	public boolean hasReadyJobs() {
		return (null != this.theReadyQueue.peek());
	}

	@Override
	public boolean makeRun() {
		if (!this.hasJobsQueued()) {
			return false;
		}

		if (!this.theReadyQueue.isEmpty()) {
			final Job elem = this.theReadyQueue.poll();
			this.currentlyRunningJob = elem;
			if (elem.getState() != Thread.State.TERMINATED) {
				if (elem.isAlive()) {
					elem.getMyCondition().signal();
				} else {
					elem.start();
					// try {
					// elem.getMyCondition().await();
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }
				}
			}
		} else {
			this.theInputQueue.poll().getMyCondition().signal();
		}
		return true;
	}

	@Override
	public void remove(Job J) {
		this.theReadyQueue.remove(J);
	}

	@Override
	public void startIO() {
		this.theInputQueue.add(this.currentlyRunningJob);
		if (this.hasRunningJob()) {
		}
	}
}
