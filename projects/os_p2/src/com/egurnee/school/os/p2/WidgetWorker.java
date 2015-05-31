package com.egurnee.school.os.p2;

public class WidgetWorker extends Thread {

	private final static int PRODUCTION_LIMIT = 24;

	private final AssemblyLineSegment bIncoming;
	private final AssemblyLineSegment bOutgoing;
	private WorkerStatus currentStatus;
	private final int location;
	private int numberProduced;
	private Widget workingOn;

	public WidgetWorker(AssemblyLineSegment bIncoming,
			AssemblyLineSegment bOutgoing, int location) {
		this.bIncoming = bIncoming;
		this.bOutgoing = bOutgoing;
		this.location = location;
		this.currentStatus = WorkerStatus.WAITING;
	}

	public WorkerStatus getCurrentStatus() {
		return this.currentStatus;
	}

	@Override
	public void run() {
		while (this.numberProduced < WidgetWorker.PRODUCTION_LIMIT) {
			this.handleIncoming();
			this.currentStatus = WorkerStatus.WORKING;
			final long millis = (long) (Math.random() * 1000);
			System.out.println("Worker " + this.location + " waiting for "
								+ millis + " millis");
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {

			}
			this.handleOutgoing();
			this.currentStatus = WorkerStatus.WAITING;
		}
		this.currentStatus = WorkerStatus.FINISHED;
	}

	private void handleIncoming() {
		if (this.bIncoming != null) {
			this.workingOn = this.bIncoming.dequeue();
		} else {
			this.workingOn = new Widget();
		}
		System.out.println("Worker " + this.location + " incoming: "
							+ this.numberProduced);
	}

	private void handleOutgoing() {
		if (this.bOutgoing != null) {
			this.bOutgoing.enqueue(this.workingOn);
		}
		this.numberProduced++;
		System.out.println("Worker " + this.location + " outgoing: "
				+ this.numberProduced);
	}
}
