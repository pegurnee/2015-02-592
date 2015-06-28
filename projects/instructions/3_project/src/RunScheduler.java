package Scheduler;

/**
 * <p>Title: RunScheduler</p>
 * <p>Description: The driver class for the scheduler project.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Eastern Michigan University</p>
 * @author Matt Evett
 * @version 1.0
 */

import java.util.ArrayList;

public class RunScheduler {
  public static void main(String[] args) {
    Thread thisThread = Thread.currentThread();
    thisThread.setPriority(Thread.MAX_PRIORITY);
    SystemSimulator os = new SystemSimulator();
    ArrayList jobs = new ArrayList();

    // Here you need to parse the input file, scheduleInput.txt, to create the jobs ArrayList.
    // For testing purposes, though, I'll just hardwire a three-value list.  The
    // RunScheduler that you submit should NOT have the following three lines.
    jobs.add("1 0 300");
    jobs.add("2 100 300");
    jobs.add("3 300 300");

    JobCreator sinecure = new MattJobCreator(os);
    Submittor sub = new Submittor( jobs, os, sinecure);
    os.setPriority(8);
    os.start(); // Thread executing this instruction has higher priority, so will continue to hold cpu
    sub.setPriority(7); // Should interrupt os only when os is sleeping, but can always interrupt running Jobs
    sub.start();
    // As the driver exits, os has the highest priority, so should get the cpu....
  }
}