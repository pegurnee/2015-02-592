package com.egurnee.school.os.p2;

public class AssemblyLineSegment {
	private final static int BUFFER_SIZE = 3;
	private final Widget[] buffer;
	private int bufferPointer;

	public AssemblyLineSegment() {
		this.buffer = new Widget[AssemblyLineSegment.BUFFER_SIZE];
		this.bufferPointer = 0;
	}

	public synchronized Widget dequeue() {
		while (this.bufferPointer == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// keep trying to wait
			}
		}

		Widget toReturn = this.buffer[this.bufferPointer--];

		this.notify();

		return toReturn;
	}

	public synchronized void enqueue(Widget incomingWidget) {
		while (this.bufferPointer == AssemblyLineSegment.BUFFER_SIZE) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// keeping trying to wait
			}
		}

		this.buffer[this.bufferPointer++] = incomingWidget;

		this.notify();
	}
}
