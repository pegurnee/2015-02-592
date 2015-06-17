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
	private final ConcurrentLinkedQueue<Job> theInputQueue = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Job> theReadyQueue = new ConcurrentLinkedQueue<>();

	@Override
	public synchronized void add(Job J) {
		System.out.println(Thread.currentThread()
							+ " adding process to ready queue.");
		this.notify();
		this.theReadyQueue.add(J);
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
	public synchronized void finishIO(Job j) {
		// this.clearRunningJob();
		// this.notify();

		j.getMyCondition().signal();
		this.theReadyQueue.add(j);
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

		if (!this.theReadyQueue.isEmpty()) {
			final Job elem = this.theReadyQueue.poll();
			this.currentlyRunningJob = elem;
			if (elem.getState() != Thread.State.TERMINATED) {
				if (elem.isAlive()) {
					// TODO
					try {
						elem.getMyCondition().await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
		final Job elem = this.theInputQueue.poll();
		elem.start();
		this.currentlyRunningJob = elem;
	}
}
