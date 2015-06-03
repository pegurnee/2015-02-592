package com.egurnee.school.os.p3.sample;

import java.util.ArrayList;

/**
 * <p>Title: GanntChart</p>
 * <p>Description: Maintain data necessary to render a Gannt chart.</p>
 * <p>Copyright: Copyright (c) 2015, 2004 by Matt Evett</p>
 * @author Matt Evett
 * @version 2.0
 * simulates the com.egurnee.school.os.p3.sample
 */

public class GanntChart {
	private long systemStartTime; // wall time when the Gannt chart starts.  Is used
								// to display all timings as relative to this time
	private ArrayList<GanntRecord> events = new ArrayList<GanntRecord>();

	public GanntChart(){

	}
	
	public void start(){
		systemStartTime = System.currentTimeMillis(); // set os start time
	}
	
	public void recordEvent(long startTime, long endTime, String eventDescriptor) {
		events.add(new GanntRecord(startTime, endTime, eventDescriptor));
	}
	
	public void end() {
		long endTime = System.currentTimeMillis();
	    events.add(new GanntRecord(endTime, endTime, "FINISHED"));
	}
	
	public void print() {
		System.out.println("TO_DO GanntChart.print not yet implemented");
	}
	
	/**
	 * Inner class to record the data of one Gannt chart event
	 * @author matt
	 *
	 */
	private class GanntRecord {
		long startTime;
		long endTime;
		String eventDescriptor;
		
		GanntRecord(long start, long end, String descrip){
			startTime = start;
			endTime = end;
			eventDescriptor = descrip;
		}
	}

}
