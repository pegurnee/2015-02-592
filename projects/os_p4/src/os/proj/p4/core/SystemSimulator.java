package os.proj.p4.core;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import os.proj.p4.io.GanntChart;
import os.proj.p4.work.Job;
import os.proj.p4.work.JobWorkable;

public class SystemSimulator extends Thread {
	private static final int ILLEGAL_TERMINATION = -20;

	private final GanntChart chart = new GanntChart();
	private volatile boolean jobsRemainToBeSubmitted = true;

	private final Scheduler myScheduler;
	private final ReentrantLock singleThreadMutex;

	public SystemSimulator(Scheduler s) {
		this.singleThreadMutex = new ReentrantLock();
		this.myScheduler = s;
	}

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
		final IODevice ioDevice = new IODevice((Job) Thread.currentThread(),
				msec, this.myScheduler, this);
		this.myScheduler.clearRunningJob();
		this.myScheduler.startIO();
		ioDevice.start();
	}

	public void exit() {
		this.myScheduler.clearRunningJob();
	}

	public ReentrantLock getSingleThreadMutex() {
		return this.singleThreadMutex;
	}

	public void noMoreJobsToSubmit() {
		this.jobsRemainToBeSubmitted = false;
	}

	public synchronized void recordRunTime() {
		Job terminatingJob = (Job) Thread.currentThread();
		Job schedulersRunning = this.myScheduler.getRunningJob();

		if (!terminatingJob.equals(schedulersRunning)) {
			System.err.println("the world is broken, "
					+ "also I didn't do everything correctly.");
			System.out.println(terminatingJob + "\n" + schedulersRunning);
			System.exit(ILLEGAL_TERMINATION);
		}

		this.chart.recordEvent(terminatingJob.getStartTime(),
				System.currentTimeMillis(), terminatingJob.getNameOf());
	}

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

			if (!this.myScheduler.makeRun()) {
				break;
			}

			if (this.myScheduler.hasRunningJob()) {
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
