package com.egurnee.school.os.p3;

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
 */

class SystemSimulator extends Thread {
	private static final int ILLEGAL_TERMINATION = -20;
	// Used to store information to create a Gannt chart
	private final GanntChart chart = new GanntChart();
	private volatile boolean jobsRemainToBeSubmitted = true; // true if not all
	// jobs have yet
	// been
	// submitted
	private final Scheduler myScheduler; // com.egurnee.school.os.p3 for jobs

	private final ReentrantLock singleThreadMutex; // Used to guarantee that

	// only

	// ...one of either the OS or any Job thread is running at any one time.

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

	public void AddNewProcess(String name, String burstDescription,
			JobWorkable workToDo) {
		Job newJob = new Job(burstDescription, this, name, workToDo);
		this.myScheduler.add(newJob);
	}

	/*
	 * The basic structure of this method is straightforward: the simulator sits
	 * in a loop, sleeping. The simulator awakens only when it is interrupted
	 * ("poked").
	 */

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
		// remove job from scheduler, record data into
		// gannt chart
		Job terminatingJob = (Job) Thread.currentThread(); // reference to
		// calling thread
		Job schedulersRunning = this.myScheduler.getRunningJob();
		/*
		 * If all is going well, terminatingJob and schedulersRunning should be
		 * equal. I've left both here for demonstration purposes--really only
		 * need one.
		 */

		// store job gannt data
		this.chart.recordEvent(terminatingJob.getStartTime(),
				System.currentTimeMillis(), terminatingJob.getNameOf());
		this.myScheduler.clearRunningJob(); // remove job from array list
		terminatingJob.getMyCondition().signal(); // This should release the OS
		// to do its thing
		this.singleThreadMutex.unlock(); // N.B. this code is only executed by a
		// Job thread
	}

	/*
	 * adds given job, j, to the ready set. Invoked by a Submittor. Keep in mind
	 * that j might not start running immediately, depending on whether another
	 * job is already running.
	 */

	public ReentrantLock getSingleThreadMutex() {
		return this.singleThreadMutex;
	}

	/*
	 * public noMoreJobsToSubmit() called by the Submittor when the last Job has
	 * been submitted. The simulator should use this information to eventually
	 * terminate when all Jobs have finished.
	 */
	public void noMoreJobsToSubmit() {
		this.jobsRemainToBeSubmitted = false;
	}

	@Override
	public void run() {
		long currentIdleTimeStart; // Wall time when current idle period started
		long currentIdleTimeEnd; // Wall time when current idle period ended

		// The OS thread should be started before any Job thread so it can gain
		// this lock first
		this.singleThreadMutex.lock();
		this.chart.start();

		// loop while we have jobs left to be scheduled or
		// com.egurnee.school.os.p3.sample has jobs scheduled
		while (this.jobsRemainToBeSubmitted || this.myScheduler.hasJobs()) {
			/*
			 * If readyQ is empty and no job running, OS will block on readyQ,
			 * submittor will signal it. If there's a running Job, TimeSlice
			 * will interrupt that, not the OS.
			 */

			currentIdleTimeStart = System.currentTimeMillis(); // start idle
			// timer
			// If there are no jobs to schedule, block on readyQ, waiting on
			// Submittor to signal it.
			this.myScheduler.blockTilThereIsAJob();
			currentIdleTimeEnd = System.currentTimeMillis(); // end idle timer
			if (currentIdleTimeEnd > currentIdleTimeStart) {
				this.chart.recordEvent(currentIdleTimeStart,
						currentIdleTimeEnd, "IDLE");
			}

			this.myScheduler.makeRun(); // the next Job should start running but
			// immediately block on OS mutex lock
			System.out.println("TO_DO Finish SystemSimulator.run()");

			while (this.myScheduler.hasRunningJob()) {
				try {
					this.myScheduler.getRunningJob().getMyCondition().await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			/*
			 * Provide code that uses the Job's Condition to block the kernel
			 * simulator thread (i.e., the thread that is executing this code).
			 * Use the await() method to do this. This will establish the mutex
			 * for the kernel and the Jobs.
			 */

			// Should get to here when that Job completes (calls Exit).
		} // exit loop, we have no jobs left and none scheduled
		this.chart.end();
		// print gannt chart
		this.chart.print();
	}

}
