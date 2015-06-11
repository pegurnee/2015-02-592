package com.egurnee.school.os.p4;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>
 * Title: FCFSScheduler
 * </p>
 * <p>
 * Description: Component of the simulate operating system that encapsulates
 * FCFS job scheduling.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015, 2004
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Matt Evett
 * @version 2.0
 * @author eddie
 * @version 2.5
 */

public class FCFSScheduler extends Scheduler {
	private final ConcurrentLinkedQueue<Job> theJobQueue = new ConcurrentLinkedQueue<Job>();

	@Override
	public synchronized void add(Job J) {
		this.notify();
		this.theJobQueue.add(J);
	}

	/**
	 * blockTilThereIsAJob() Invoked by OS simulator when it wants to get a new
	 * Job to run. Will block if the ready queue is empty until a Job is added
	 * to the queue.
	 */
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
	public boolean hasJobsQueued() {
		return this.theJobQueue.peek() != null;
	}

	/**
	 * If the ready queue is empty, return false. Otherwise, start the next job
	 * in the queue, returning true. If the queue is empty return false. Make
	 * the next job in the ready queue run. You should probably invoke
	 * Thread.start() on it.
	 */
	@Override
	public boolean makeRun() {
		if (!this.hasJobsQueued()) {
			return false;
		}

		final Job elem = this.theJobQueue.poll();
		this.currentlyRunningJob = elem;

		elem.start();
		return true;
	}

	@Override
	public void remove(Job J) {
		this.theJobQueue.remove(J);
	}
}
