package com.egurnee.school.os.p4.work;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import com.egurnee.school.os.p4.core.SystemSimulator;

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

		// this.startTime = System.currentTimeMillis();
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
		// System.out.println(Thread.currentThread());

		//

		// this.getMyCondition().signal();
	}

	private synchronized void doIO() {
		this.myOS.recordRunTime();
		this.getMyCondition().signal();
		// this.myCondition.signal();
		// this.myOS.getSingleThreadMutex().unlock();
		// new IODevice(this, this.burstTimes.removeFirst(), this.myOS);
		// this.myCondition.signal();
		// this.myOS.getSingleThreadMutex().unlock();
		final int ioTime = this.burstTimes.removeFirst();
		this.myOS.doIO(ioTime);

		try {
			this.myCondition.await();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// this.myOS.getSingleThreadMutex().unlock();
		// this.myOS.getSingleThreadMutex().lock();
		// try {
		// Job.yield();
		// this.wait(10);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// this.myOS.getSingleThreadMutex().lock();
		// try {
		// System.out.println("here");
		// this.myCondition.await();
		// System.out.println("and here");
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}
}
