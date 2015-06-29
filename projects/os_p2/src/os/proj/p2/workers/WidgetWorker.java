package os.proj.p2.workers;

import os.proj.p2.Widget;
import os.proj.p2.assembly.AssemblyLineSegment;

public class WidgetWorker extends Thread {

	private final static int PRODUCTION_LIMIT = 24;

	private final AssemblyLineSegment bIncoming;
	private final AssemblyLineSegment bOutgoing;
	private WorkerStatus currentStatus;
	private boolean enabled;
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

	public void disable() {
		this.enabled = false;
	}

	public WorkerStatus getCurrentStatus() {
		return this.currentStatus;
	}

	@Override
	public void run() {
		this.enabled = true;
		while ((this.numberProduced < WidgetWorker.PRODUCTION_LIMIT)
				&& this.enabled) {
			this.handleIncoming();
			this.workOnWidget();
			this.handleOutgoing();
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
		this.currentStatus = WorkerStatus.WAITING;
	}

	private void workOnWidget() {
		this.currentStatus = WorkerStatus.WORKING;
		final long millis = (long) (Math.random() * 2_500);
		System.out.println("Worker " + this.location + " waiting for " + millis
				+ " millis");
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {

		}
	}
}
