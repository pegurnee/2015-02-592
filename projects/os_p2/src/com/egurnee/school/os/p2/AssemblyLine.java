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
		for (int i = 0; i < (this.workers.length - 1); i++) {
			this.workers[i] = new WidgetWorker(this.segments[i],
					this.segments[i + 1], i);
		}
	}
}
