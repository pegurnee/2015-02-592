package os.proj.p4.work;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import os.proj.p4.core.SystemSimulator;

public class Job extends Thread {

	private final LinkedList<Integer> burstTimes;
	private final Condition myCondition;

	private final SystemSimulator myOS;
	private final String name;

	private volatile boolean shouldRun;
	private volatile long startTime;
	private final JobWorkable work;

	public Job(LinkedList<Integer> burstTimes, SystemSimulator s, String name,
			JobWorkable workToDo) {
		this.myOS = s;
		this.myCondition = s.getSingleThreadMutex().newCondition();

		this.burstTimes = burstTimes;
		this.work = workToDo;

		this.name = name;
		this.shouldRun = false;
	}

	public void exit() {
		this.getMyCondition().signal();
		this.myOS.getSingleThreadMutex().unlock();
		this.myOS.exit();
	}

	public LinkedList<Integer> getBurstTime() {
		return (this.burstTimes);
	}

	public Condition getMyCondition() {
		return this.myCondition;
	}

	public synchronized String getNameOf() {
		return (this.name);
	}

	public long getStartTime() {
		return (this.startTime);
	}

	public synchronized void pleaseStop() {
		this.shouldRun = false;
	}

	@Override
	public void run() {
		this.myOS.getSingleThreadMutex().lock();

		this.doCPU();
		while (!this.burstTimes.isEmpty()) {
			this.doIO();
			this.doCPU();
		}

		this.exit();
	}

	public synchronized boolean shouldRun() {
		return (this.shouldRun);
	}

	@Override
	public String toString() {
		return "[" + this.name + "]";
	}

	private void doCPU() {
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

		/*
		 * Need to add record time at end of CPU, otherwise issues
		 */
		this.myOS.recordRunTime();
	}

	private synchronized void doIO() {
		this.getMyCondition().signal();

		final int ioTime = this.burstTimes.removeFirst();
		this.myOS.doIO(ioTime);

		try {
			this.myCondition.await();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
