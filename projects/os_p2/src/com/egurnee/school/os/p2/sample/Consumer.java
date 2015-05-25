package com.egurnee.school.os.p2.sample;
/**
 * Consumer.java
 *
 * This is the consumer thread for the bounded buffer problem.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 */

import java.util.Date;

public class Consumer extends Thread {
	private final BoundedBuffer buffer;

	public Consumer(BoundedBuffer b) {
		this.buffer = b;
	}

	@Override
	public void run() {
		Date message;

		while (true) {
			BoundedBuffer.napping();

			// consume an item from the buffer
			System.out.println("Consumer wants to consume.");

			message = (Date) this.buffer.remove();
		}
	}
}
