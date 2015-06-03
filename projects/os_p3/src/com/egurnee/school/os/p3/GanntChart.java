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
 * @version 2.0 simulates the com.egurnee.school.os.p3
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
		System.out.println("TO_DO GanntChart.print not yet implemented");
	}

	public void recordEvent(long startTime, long endTime, String eventDescriptor) {
		this.events.add(new GanntRecord(startTime, endTime, eventDescriptor));
	}

	public void start() {
		this.systemStartTime = System.currentTimeMillis(); // set os start time
	}

}
