package com.egurnee.school.os.p4;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

/**
 * <p>
 * Title: Job
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
 * @version 2.0 You should ensure that your program runs using the class
 *          (MattJob). I will replace the MattJob class with one of my own, but
 *          it is guaranteed to extend Job. My subclass will override the run()
 *          method, but it is guaranteed that my run() method will cause
 *          pleaseStop() to be invoked after the CPU burst has expired, and it
 *          will check the shouldRun() method about once every 10 milliseconds.
 *          Once shouldRun() returns false, my run()will call Exit(), just
 *          before returning (i.e., terminating).
 */

class Job extends Thread {

	private final LinkedList<Integer> burstTimes;
	private final Condition myCondition;

	private final SystemSimulator myOS;
	private final String name;

	private volatile boolean shouldRun;
	private volatile long startTime;
	private final JobWorkable work;

	/**
	 * The burstDescription consists of a single integer, y, which is the the
	 * CPU burst duration. In a later version of this program we'll augment the
	 * descriptors to allow for a sequence of CPU and IO burst lengths.
	 */
	public Job(LinkedList<Integer> burstTimes, SystemSimulator s, String name,
			JobWorkable workToDo) {
		this.myOS = s;
		this.myCondition = s.getSingleThreadMutex().newCondition();

		this.burstTimes = burstTimes;
		this.work = workToDo;

		this.name = name;
		this.shouldRun = false;
	}

	/**
	 * should be last instruction in run(). Exit should eventually invoke
	 * myOS.exit();
	 */
	public void exit() {
		this.myOS.exit();
	}

	public Condition getMyCondition() {
		return this.myCondition;
	}

	/**
	 * Can do pretty much anything but must return after the CPU burst time has
	 * elapsed. (Note that the Job's run-clock should not start "ticking" until
	 * run() is invoked!
	 */
	@Override
	public void run() {
		this.myOS.getSingleThreadMutex().lock();

		// this.startTime = System.currentTimeMillis();
		this.doCPU();
		while (!this.burstTimes.isEmpty()) {
			this.doIO();
			this.doCPU();
		}

		this.exit();
	}

	@Override
	public String toString() {
		return "[" + this.name + "]";
	}

	/**
	 *
	 */
	private void doCPU() {

		// this.myOS.getSingleThreadMutex().lock();
		this.startTime = System.currentTimeMillis();
		int currentBurstLimit = this.burstTimes.removeFirst();

		while ((System.currentTimeMillis() - this.startTime) < currentBurstLimit) {
			this.work.doWork();
			try {
				sleep(10);
			} catch (InterruptedException e) {
				System.out
						.println(""
									+ this.name
									+ " is interrupted, hopefully only by TimeSlicer");
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread());

		// this.myOS.getSingleThreadMutex().unlock();

		// try {
		// this.wait();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	private synchronized void doIO() {
		// this.myCondition.signal();
		// this.myOS.getSingleThreadMutex().unlock();
		// new IODevice(this, this.burstTimes.removeFirst(), this.myOS);
		final int ioTime = this.burstTimes.removeFirst();
		this.myOS.doIO(ioTime);

		// this.myOS.getSingleThreadMutex().lock();
		try {
			System.out.println("here");
			this.myCondition.await();
			System.out.println("and here");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * An accessor, returning the CPU burst time of the job.
	 */
	protected LinkedList<Integer> getBurstTime() {
		return (this.burstTimes);
	}

	/**
	 * An accessor, returning the starting time of the job.
	 */
	protected long getStartTime() {
		return (this.startTime);
	}

	/**
	 * returns false when the Job's burst time has been exhausted.
	 */
	synchronized protected boolean shouldRun() {
		return (this.shouldRun);
	}

	/**
	 * return name of the job. Note that you can choose to use the inherited
	 * Thread.getName, but if so, make sure you use the "name" argument
	 * appropriately in the Job constructor, above.
	 */
	synchronized String getNameOf() {
		return (this.name);
	}

	/**
	 * will be invoked when the burst time has been exhausted. This is a simple
	 * method that sets a flag which will eventually be accessed via a call to
	 * shouldRun(). I will use a timer object to call this method at the
	 * appropriate time.
	 */
	synchronized void pleaseStop() {
		this.shouldRun = false;
	}

}
