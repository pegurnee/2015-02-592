package com.egurnee.school.os.p4;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * Title: SystemSimulator
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015, 2004 by Matt Evett
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Matt Evett
 * @version 2.0 extends Thread: this class simulates the kernel.
 * @author eddie
 * @version 2.5
 */

class SystemSimulator extends Thread {
	private static final int ILLEGAL_TERMINATION = -20;

	private final GanntChart chart = new GanntChart();
	private volatile boolean jobsRemainToBeSubmitted = true;

	private final Scheduler myScheduler;
	private final ReentrantLock singleThreadMutex;

	public SystemSimulator(Scheduler s) {
		this.singleThreadMutex = new ReentrantLock();
		this.myScheduler = s;
	}

	/**
	 * SystemSimulator() constructor is private to force the use of other
	 * constructors.
	 */
	@SuppressWarnings("unused")
	private SystemSimulator() {
		this.myScheduler = null;
		this.singleThreadMutex = null;
	}

	public void AddNewProcess(String name, LinkedList<Integer> bursts,
			JobWorkable workToDo) {
		Job newJob = new Job(bursts, this, name, workToDo);
		this.myScheduler.add(newJob);
	}

	public void doIO(int msec) {
		// try {
		// this.myScheduler.currentlyRunningJob.wait();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// this.myScheduler.currentlyRunningJob.getMyCondition().signal();
		// this.myScheduler.startIO();
		new IODevice(this.myScheduler.currentlyRunningJob, msec,
				this.myScheduler, this).start();
	}

	/**
	 * Exit() called by a Job thread to indicate that it is terminating. This
	 * should be the last instruction executed by a Job's run method. This
	 * method is meant to mimic a true system call to exit(). Note that because
	 * this method will be invoked by Job, a Thread, we can use the
	 * Thread.getCurrentThread() method to get a reference to the Job that is
	 * invoking this method.
	 *
	 * @param jobStart
	 *            = wall time when Job first started running
	 */
	public void exit() {
		Job terminatingJob = (Job) Thread.currentThread();
		Job schedulersRunning = this.myScheduler.getRunningJob();

		if (!terminatingJob.equals(schedulersRunning)) {
			System.err.println("the world is broken, "
								+ "also I didn't do everything correctly.");
			System.exit(ILLEGAL_TERMINATION);
		}

		this.chart.recordEvent(terminatingJob.getStartTime(),
				System.currentTimeMillis(), terminatingJob.getNameOf());
		this.myScheduler.clearRunningJob();
		terminatingJob.getMyCondition().signalAll();
		this.singleThreadMutex.unlock();
	}

	public ReentrantLock getSingleThreadMutex() {
		return this.singleThreadMutex;
	}

	/**
	 * public noMoreJobsToSubmit() called by the Submittor when the last Job has
	 * been submitted. The simulator should use this information to eventually
	 * terminate when all Jobs have finished.
	 */
	public void noMoreJobsToSubmit() {
		this.jobsRemainToBeSubmitted = false;
	}

	/**
	 * The basic structure of this method is straightforward: the simulator sits
	 * in a loop, sleeping. The simulator awakens only when it is interrupted
	 * ("poked").
	 */
	@Override
	public void run() {
		long currentIdleTimeStart;
		long currentIdleTimeEnd;

		this.singleThreadMutex.lock();
		this.chart.start();

		while (this.jobsRemainToBeSubmitted || this.myScheduler.hasJobs()) {

			currentIdleTimeStart = System.currentTimeMillis();
			this.myScheduler.blockTilThereIsAJob();
			currentIdleTimeEnd = System.currentTimeMillis();

			if (currentIdleTimeEnd > currentIdleTimeStart) {
				this.chart.recordEvent(currentIdleTimeStart,
						currentIdleTimeEnd, "IDLE");
			}

			this.myScheduler.makeRun();

			while (this.myScheduler.hasRunningJob()) {
				try {
					this.myScheduler.getRunningJob().getMyCondition().await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		this.chart.end();
		this.chart.print();
	}

}
