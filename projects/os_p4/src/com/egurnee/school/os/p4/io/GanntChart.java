package com.egurnee.school.os.p4.io;

import java.util.ArrayList;

public class GanntChart {

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
		for (int i = 0; i < 1_000_000; i++) {
			i++;
		}
		test.recordEvent(System.currentTimeMillis(),
				System.currentTimeMillis(), "B");
		for (int i = 0; i < 100_000_000; i++) {
			i++;
		}
		test.recordEvent(System.currentTimeMillis(),
				System.currentTimeMillis(), "C");
		for (int i = 0; i < 1_000_000_000; i++) {
			i++;
		}
		test.end();
		test.print();
	}

	private final ArrayList<GanntRecord> events;
	private long systemStartTime;

	public GanntChart() {
		this.events = new ArrayList<>();
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
		this.systemStartTime = System.currentTimeMillis();
	}

}
