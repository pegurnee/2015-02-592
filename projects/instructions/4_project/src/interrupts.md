# Interrupting Threads

Here is a brief tutorial on how to have one thread interrupt another.

 

Let *myThread* be a thread. In your case it will probably be the scheduler thread. (Note that each `JobThread` has a *myScheduler* field that is initialized to the scheduler.) To interrupt the thread, use

    myThread.interrupt();

Now, there are three states the interrupted thread (your scheduler) will be in: sleeping, waiting, or executing normally. The sleeping and waiting situations are analogous. You'll have code something like:

```
try {
    sleep(X);
}
catch (InterruptedException e) {
    //Check your java doc for getting info about e
    //The code in this catch clause will be executed only if the
    //thread is interrupted during its sleep.
}
```

You could substitute "*wait()*" for "*sleep(X)*" and this would work
the same way.

Now, handling the third possible state ("running normally") requires active effort on the part of the "interrupted" thread. In that case, there is no exception, and so none to be caught. However, whenever a thread is interrupted, a flag is set within that thread. Your interrupted thread may need to periodically check this flag explicitly to see if it has been interrupted.  
You do that with code such as:

    if (interrupted()) { ... }
    
This function call also clears the flag. Note that this is a static method of the `Thread` class. It checks to see if the current thread has been interrupted. If the above code is being executed in a class that is not a subclass of `Thread`, you'll need to use the following code instead:

    if (Thread.interrupted()) { .... }
    
One annoyance with interrupts is that the interrupted thread cannot explicitly determine who was the interrupter. In that case, you may have to use some kind of flag to determine the cause. Thus, if, say, a thread A, might be interrupted by either thread B or C, then A's code might look something like:

```
try {
    sleep(X);
    }
    catch (InterruptedException e) {
        //I know I was interrupted by either B or C.
        //so I might want to execute code here that will determine which, and for
        //what reason.
	    if (flagB)
            ...execute code that should be triggered by B's interrupt
        else
            ...execute code that should be triggered by C's interrupt
    }
}
```