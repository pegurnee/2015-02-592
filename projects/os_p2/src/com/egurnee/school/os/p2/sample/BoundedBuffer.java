package com.egurnee.school.os.p2.sample;
/**
 * BoundedBuffer.java
 *
 * This program implements the bounded buffer using Java synchronization.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999 Copyright 2000 by Greg Gagne, Peter Galvin, Avi
 *          Silberschatz Applied Operating Systems Concepts - John Wiley and
 *          Sons, Inc.
 */

public class BoundedBuffer {
	public static final int NAP_TIME = 5;

	private static final int BUFFER_SIZE = 5;

	// producer and consumer will call this to nap
	public static void napping() {
		int sleepTime = (int) (NAP_TIME * Math.random());
		try {
			Thread.sleep(sleepTime * 1000);
		} catch (InterruptedException e) {
		}
	}

	private final Object[] buffer;

	private int count; // number of items in the buffer
	private int in; // points to the next free position in the buffer

	private int out; // points to the next full position in the buffer

	public BoundedBuffer() {
		// buffer is initially empty
		this.count = 0;
		this.in = 0;
		this.out = 0;

		this.buffer = new Object[BUFFER_SIZE];
	}

	public synchronized void enter(Object item) {
		while (this.count == BUFFER_SIZE) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}

		// add an item to the buffer
		++this.count;
		this.buffer[this.in] = item;
		this.in = (this.in + 1) % BUFFER_SIZE;

		if (this.count == BUFFER_SIZE) {
			System.out.println("Producer Entered " + item + " Buffer FULL");
		} else {
			System.out.println("Producer Entered " + item + " Buffer Size = "
								+ this.count);
		}

		this.notify();
	}

	// consumer calls this method
	public synchronized Object remove() {
		Object item;

		while (this.count == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}

		// remove an item from the buffer
		--this.count;
		item = this.buffer[this.out];
		this.out = (this.out + 1) % BUFFER_SIZE;

		if (this.count == 0) {
			System.out.println("Consumer Consumed " + item + " Buffer EMPTY");
		} else {
			System.out.println("Consumer Consumed " + item + " Buffer Size = "
								+ this.count);
		}

		this.notify();

		return item;
	}
}
