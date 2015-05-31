package com.egurnee.school.os.p2;

public class AssemblyLine {
	private final static int DEFAULT_NUMBER_OF_WIDGETS = 24;
	private final static int DEFAULT_NUMBER_OF_WORKERS = 4;

	private final int numberOfWidgetsDesired;
	private final AssemblyLineSegment[] segments;
	private final WidgetWorker[] workers;

	public AssemblyLine() {
		this(AssemblyLine.DEFAULT_NUMBER_OF_WORKERS,
				AssemblyLine.DEFAULT_NUMBER_OF_WIDGETS);
	}

	public AssemblyLine(int numberOfWorkers, int numberOfWidgetsDesired) {
		this.numberOfWidgetsDesired = numberOfWidgetsDesired;

		this.segments = new AssemblyLineSegment[numberOfWorkers - 1];
		for (int i = 0; i < this.segments.length; i++) {
			this.segments[i] = new AssemblyLineSegment();
		}

		this.workers = new WidgetWorker[numberOfWorkers];

		this.workers[0] = new WidgetWorker(null, this.segments[0], 0);
		for (int i = 1; i < (this.workers.length - 1); i++) {
			this.workers[i] = new WidgetWorker(this.segments[i - 1],
					this.segments[i], i);
		}
		this.workers[this.workers.length - 1] = new WidgetWorker(
				this.segments[this.segments.length - 1], null,
				this.workers.length - 1);
	}

	public int[] getCounts() {
		int[] toReturn = new int[this.segments.length];
		for (int i = 0; i < this.segments.length; i++) {
			toReturn[i] = this.segments[i].getBufferCount();
		}
		return toReturn;
	}

	public WorkerStatus[] getStatuses() {
		WorkerStatus[] toReturn = new WorkerStatus[this.workers.length];
		for (int i = 0; i < this.workers.length; i++) {
			toReturn[i] = this.workers[i].getCurrentStatus();
		}
		return toReturn;
	}

	public void runAll() {
		for (int i = 0; i < this.workers.length; i++) {
			this.workers[i].start();
		}
	}
}
