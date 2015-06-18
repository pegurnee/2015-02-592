package com.egurnee.school.os.p4.work;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.egurnee.school.os.p4.core.SystemSimulator;

public class Submittor extends Thread {
	private final ArrayList<String> myJobDescs;
	private final SystemSimulator mySystem;
	private final WorkFactory myWorkCreator;

	private StringTokenizer st;

	public Submittor(ArrayList<String> jobDescriptions, SystemSimulator s,
			WorkFactory progenitor) {
		super("Submittor");
		this.myJobDescs = jobDescriptions;
		this.mySystem = s;
		this.myWorkCreator = progenitor;
	}

	@Override
	public void run() {
		final int limit = this.myJobDescs.size();
		for (int i = 0; i < limit; i++) {
			LinkedList<Integer> bursts = new LinkedList<>();

			this.st = new StringTokenizer(this.myJobDescs.get(i));

			String id = "JOB " + this.st.nextToken();
			int delay = Integer.parseInt(this.st.nextToken());

			while (this.st.hasMoreTokens()) {
				bursts.add(Integer.parseInt(this.st.nextToken()));
			}

			try {
				sleep(delay);
			} catch (InterruptedException e) {
				System.err.println("Submittor should never be interrupted");
				e.printStackTrace();
			}

			this.mySystem.AddNewProcess(id, bursts,
					this.myWorkCreator.createWork());
		}
		this.mySystem.noMoreJobsToSubmit();
	}
}
