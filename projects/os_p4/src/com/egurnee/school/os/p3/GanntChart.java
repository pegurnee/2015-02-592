package com.egurnee.school.os.p3;

import java.util.ArrayList;

/**
 * <p>
 * Title: GanntChart
 * </p>
 * <p>
 * Description: Maintain data necessary to render a Gannt chart.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015, 2004 by Matt Evett
 * </p>
 *
 * @author Matt Evett
 * @version 2.0 simulates the scheduler
 * @author eddie
 * @version 2.5 displays the gannt chart
 */

public class GanntChart {
	/**
	 * Inner class to record the data of one Gannt chart event
	 *
	 * @author matt
	 *
	 */
	private class GanntRecord {
		long endTime;
		String eventDescriptor;
		long startTime;

		GanntRecord(long start, long end, String descrip) {
			this.startTime = start;
			this.endTime = end;
			this.eventDescriptor = descrip;
		}
	}

	public static void main(String[] args) {
		GanntChart test = new GanntChart();
		test.start();
		test.recordEvent(System.currentTimeMillis(),
				System.currentTimeMillis(), "A");
		test.recordEvent(System.currentTimeMillis(),
				System.currentTimeMillis(), "B");
		test.recordEvent(System.currentTimeMillis(),
				System.currentTimeMillis(), "C");
		test.end();
		test.print();

	}

	// to display all timings as relative to this time
	private final ArrayList<GanntRecord> events = new ArrayList<GanntRecord>();

	private long systemStartTime; // wall time when the Gannt chart starts. Is

	// used

	public GanntChart() {

	}

	public void end() {
		long endTime = System.currentTimeMillis();
		this.events.add(new GanntRecord(endTime, endTime, "FINISHED"));
	}

	public void print() {
		System.out.println("Gannt Chart:");
		System.out.printf("%4s%-11s %11s    %-11s", " ", "Burst Start",
				"Burst End", "JOB");
		for (GanntRecord ganntRecord : this.events) {
			System.out.printf("%n%4s%11s %11s    %-11s", " ",
					ganntRecord.startTime - this.systemStartTime,
					ganntRecord.endTime - this.systemStartTime,
					ganntRecord.eventDescriptor);
		}
	}

	public void recordEvent(long startTime, long endTime, String eventDescriptor) {
		this.events.add(new GanntRecord(startTime, endTime, eventDescriptor));
	}

	public void start() {
		this.systemStartTime = System.currentTimeMillis(); // set os start time
	}

}
