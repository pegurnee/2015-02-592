# Null References with Thread.setPriority()

A word of warning: if you are getting `NullPointerException` inside *setPriority()*, it is almost certainly because that thread has already died (i.e., exited its *run()* method). How might this happen? Here's an example:

Suppose your scheduler has a reference to the currently running job: *currentJob*, and a *readyQ*. A job (running at priority 5) invokes its *Exit()* method. That method (of the `Scheduler` class) contains code something like:

```
System.out.println("Job is exiting");
currentJob = null; // I'm no longer running
mySimulator.interrupt();
```

Now suppose a timeout occurs just after that second line is executed. Now there'll be a null reference exception when the `Simulator` tries to execute this code:

    currentJob.setPriority(1);
    
An even more subtle problem can derive from how that *interrupt()* is handled. Suppose the scheduler code is just:

```
System.out.println("Job is exiting");
mySimulator.interrupt();
```

Interrupts are not instantaneous, so possibly the `Job` thread will have terminated by the time the `Simulator` executes the same *setPriority(1)* call. Now there will be a `NullPointerException` **within** the code of *setPriority()*.

The overall problem arises because we have two or more threads trying to manipulate the same variable (*currentThread*). In general, this is a bad idea, unless we **synchronize** access to that variable. If both `Job` theads and the `Simulator` can change *currentJob*, then all code where *currentJob*'s value can be changed should be **synchronized**.  
That could be done via something like this:

```
synchronized (this) { // this == the Scheduler (or Simulator)
    System.out.println("Job is exiting");
    currentJob = null; // I'm no longer running
    mySimulator.interrupt();
}
```

Be careful you don't synchronize overly large blocks of code, or you can end up needlessly blocking other threads. I had only two sections of synchronized code, both fewer than four lines, in my program.