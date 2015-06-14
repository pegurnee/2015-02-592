
# Hints for Completing a Simple Scheduler with IO

## The path to victory:

1. Modify `Job.java` so that *burstTime* is now *LinkedList&lt;Integer&gt; burstTimes*
	A. Change the Job constructors so that they take a *LinkedList&lt;Integer&gt;* argument.
	B. Modify `Submittor.java` to parse the input file for the string of burst durations, creating a *LinkedList&lt;Integer&gt;* of them 
	C. Modify `SystemSimulator.AddNewProcess()` to take *LinkedList&lt;Integer&gt;* as an argument.
2. To `Scheduler.java` add
	A. **public abstract void startIO();**
	B. **public abstract void finishIO(Job j);**
	C. **public abstract boolean hasReadyJobs();** // true if readyQ not empty.  Consider when it is appropriate to use this as opposed to hasJobs, hasJobsQueued.
3. Modify `FCFSScheduler.java`
	A. Add a data structure, an “input queue”, such as a **ConcurrentLinkedQueue**, to store the Jobs that are currently blocked on an IO operation.  (Thus every Job is either the one currently running, or is on the ready queue, or this new data structure.)
	B. Implement the new abstract methods.
	C. Alter `makeRun()` to accommodate readyQ entries (Jobs) that have already been started, but were re-entered on the readyQ by a *IODevice*.  See `Thread.isAlive()`.
4. Create the `IODevice` class. 
	A. This is a simple Thread class. It will need three instance variables: the Job it is associated with, the # of msec of its IO operation, and a reference either to the kernel or to its Scheduler.
	B. Its *run()* is basically a sleep for the appropriate # of msec, followed by code that reinserts its Job back into the ready queue, (as well as removing that Job from the “input” queue.) See *Scheduler.finishIO*.
5. Alter `SystemSimulator.java` so that its *run()* will continue for so long as either the Submittor has not yet submitted all its Jobs, or there are Jobs in either the ready queue, or in the input queue (i.e. blocked on input).

I recommend structuring Job.run() like this:

    public void run()
    {
        //Should block here until the OS blocks itself on this Job's Condition
        myOS.getSingleThreadMutex().lock();
        
        doCPU();
        while(!burstTimes.isEmpty()) {  // While there remains an IO/CPU pair
            doIO();
            doCPU();
        }
        exit();  //exit needs to signal the condition, and release the lock
    }

where *doIO()* and *doCPU()* are helper functions in `Job.java`. *doCPU()* will mostly consist of the code that was in the previous assignment’s version of *Job.run()*. *Job.doIO()* will, among other things, call `SystemSimulator.doIO(int msec)`.

You'll need to add another pair of *await()* and *signal()* on Job's `Condition` object. Consider that Job threads will invoke `SystemSimulator.doIO()`. That method will need to do several things, but most importantly it will need to send a *signal()* to the `Condition`, because the kernel should be blocked on a matching *await()* (probably in `FCFSScheduler.makeRun()`). The Job will then immediately invoke an *await()* on the same `Condition`. This will pass control to the kernel. (The `Condition` acts like a baton that the kernel and the Job pass back and forth to control who is running.) The *signal()* for this new *await()* will be invoked in `FCFSScheduler.makeRun()`.

 
