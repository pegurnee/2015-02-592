package com.egurnee.school.os.p4;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <p>
 * Title: Submittor
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015, 2004 by Matt Evett
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Matt Evett
 * @version 2.0 extends Thread: should submit new jobs to a SystemSimulator from
 *          time to time. It will run at a higher priority than any Jobs, but
 *          lower than the SystemSimulator.
 * @author eddie
 * @version 2.5
 */

class Submittor extends Thread {
	private final ArrayList<String> myJobDescs;
	private final SystemSimulator mySystem;
	private final WorkFactory myWorkCreator;

	private StringTokenizer st;

	/**
	 * @param jobDescriptions
	 *            = an array of Strings, each a line from the input file. The
	 *            syntax of this string will vary depending on the type of
	 *            scheduler we are implementing. For every type of scheduler the
	 *            first token will be the name of a job/process and the second
	 *            token will be an integer representing the number of msecs
	 *            before this job should be submitted to the OS. For a FCFS
	 *            scheduler the remainder of the string will consist of a single
	 *            token, an integer equal to the length of the simulated CPU
	 *            burst in msec.
	 * @param s
	 *            = The kernel simulator
	 * @param progenitor
	 *            = a factory for creating JobWorkable objects that will be
	 *            embedded in the Job.
	 */
	public Submittor(ArrayList<String> jobDescriptions, SystemSimulator s,
			WorkFactory progenitor) {
		super("Submittor");
		this.myJobDescs = jobDescriptions;
		this.mySystem = s;
		this.myWorkCreator = progenitor;
	}

	/**
	 * Sleeps for a bit, creates work to be accomplished in a Job and submits
	 * this and a description of the work to its system simulator, and repeats.
	 * See my boilerplate code, Submittor.java.
	 */
	@Override
	public void run() {
		final int limit = this.myJobDescs.size();
		for (int i = 0; i < limit; i++) {
			this.st = new StringTokenizer(this.myJobDescs.get(i));
			String id = "JOB " + this.st.nextToken();
			int delay = Integer.parseInt(this.st.nextToken());
			ArrayList<Integer> bursts = new ArrayList<Integer>();
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
