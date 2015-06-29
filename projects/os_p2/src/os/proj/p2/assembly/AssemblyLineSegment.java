package os.proj.p2.assembly;

import os.proj.p2.Widget;

public class AssemblyLineSegment {
	private final static int BUFFER_SIZE = 3;
	private final Widget[] buffer;
	private int bufferCount;
	private int bufferHead;
	private int bufferTail;

	public AssemblyLineSegment() {
		this.buffer = new Widget[AssemblyLineSegment.BUFFER_SIZE];
		this.bufferHead = 0;
		this.bufferTail = 0;
		this.bufferCount = 0;
	}

	public synchronized Widget dequeue() {
		while (this.bufferCount == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// keep trying to wait
			}
		}

		this.bufferCount--;
		Widget toReturn = this.buffer[this.bufferTail];
		this.bufferTail = (this.bufferTail + 1)
				% AssemblyLineSegment.BUFFER_SIZE;

		this.notify();

		return toReturn;
	}

	public synchronized void enqueue(Widget incomingWidget) {
		while (this.bufferCount == AssemblyLineSegment.BUFFER_SIZE) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// keeping trying to wait
			}
		}

		this.bufferCount++;
		this.buffer[this.bufferHead] = incomingWidget;
		this.bufferHead = (this.bufferHead + 1)
				% AssemblyLineSegment.BUFFER_SIZE;

		this.notify();
	}

	public int getBufferCount() {
		return this.bufferCount;
	}
}
