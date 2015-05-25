package com.egurnee.school.os.p2.sample;
/**
 * Producer.java
 *
 * This is the producer thread for the bounded buffer problem.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 */

import java.util.Date;

public class Producer extends Thread {
	private final BoundedBuffer buffer;

	public Producer(BoundedBuffer b) {
		this.buffer = b;
	}

	@Override
	public void run() {
		Date message;

		while (true) {
			BoundedBuffer.napping();

			// produce an item & enter it into the buffer
			message = new Date();
			System.out.println("Producer produced " + message);

			this.buffer.enter(message);
		}
	}
}
