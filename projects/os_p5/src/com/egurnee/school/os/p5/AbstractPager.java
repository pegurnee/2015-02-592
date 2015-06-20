package com.egurnee.school.os.p5;

import java.util.Iterator;

/*
 CLASS: Pager

 by Matthew Evett, 1999

 This is an abstract class to serve as the parent class of the four different
 types of pagers.  As such, this class defines the required API for the same.

 constructors:

 Pager();
 Pager(int numFrames);
 See SetNumFrames

 accessors:

 int NumFaults();
 int Accesses();
 Number of page requests the pager has handled so far.  This can be
 considered the "time" of the simulation.

 operators:

 void Run(const PageSeq &seq);
 Run the simultion & collect stats.  This method makes use of the polymorphism provided
 by DoPageFault and DoPageAccess; for any pager, P, of any type, P.Run() will execute
 Pager::Run.
 void Print();
 Print the fault table resulting from most recent Run
 int SetNumFrames(int num);
 Set the number of frames to be used during a run
 void PrintStats(int optimal);   // Print one line of final results table of running the pager.

 Each Pager must provide the following interface:

 virtual char * Name() = 0;
 The name of the pager
 virtual int DoPageFault() = 0;
 Take care of whatever bookeeping is necessary before a new page is placed
 in the Pager's myFrames.
 Returns index of the frame where the new page's ID is to be placed (the actual
 replacement is done in Pager::Run.)
 virtual void DoPageAccess(int frameID) = 0;
 Handle a page access that does not incur a fault.  The page accessed is that
 currently stored in the frame with index frameID.


 protected:
 int myNumFaults;
 Number of page faults handled so far.
 Frames myFrames;
 Contents of the frames.  Basically, just an array of page IDs.
 PrintBuffer myHistory;
 Used during simulation to keep a history of the contents of the frames.  Later,
 this information can be used to print the page fault tables.
 int myAccesses;
 Number of page accesses (including page faults) handled so far.

 */

public abstract class AbstractPager extends Thread {
	protected int currentPage;
	protected int myAccesses;
	protected Frames myFrames;
	protected PrintBuffer myHistory;

	protected int myNumFaults;
	protected PagingScheme theScheme;
	protected PageSeq theSequence;

	public AbstractPager() {
		this.myNumFaults = 0;
		this.myAccesses = 0;
		this.myFrames = new Frames();
		this.myHistory = new PrintBuffer();
	}

	public AbstractPager(int numFrames) {
		this.myNumFaults = 0;
		this.SetNumFrames(numFrames);
	}

	public AbstractPager(PageSeq sequence) {
		this.myNumFaults = 0;
		this.SetNumFrames(sequence.getFramesOfMemory());
	}

	public AbstractPager(PagingScheme theScheme) {
		this.theScheme = theScheme;
		this.myNumFaults = 0;
		this.myAccesses = 0;
		this.myFrames = new Frames();
		this.myHistory = new PrintBuffer();
	}

	public final int Accesses() {
		return this.myAccesses;
	}

	public final void accessPage() {
		this.myAccesses++;
	}

	public abstract void DoPageAccess(int pagePos);

	public abstract int DoPageFault();

	public final void faultPage() {
		this.myNumFaults++;
	}

	public final String Name() {
		return this.theScheme.toString();
	}

	public final int NumFaults() {
		return this.myNumFaults;
	}

	public final void Print() {
		System.out.println(this.Name() + ":");
		System.out.println(this.myHistory);
	}

	public final void PrintStats(int optimal) {
		System.out.printf("%-8s %8s %8.2f%%%n", this.theScheme,
				this.NumFaults(), (this.NumFaults() / (double) optimal) * 100);
	}

	@Override
	public final void run() {
		if (this.theSequence == null) {
			return;
		}
		this.Run(this.theSequence);
	}

	// TODO
	public void Run(PageSeq seq) {
		this.theSequence = seq;
		this.myNumFaults = 0;
		this.myAccesses = 0;

		this.SetNumFrames(seq.getFramesOfMemory());

		final Iterator<Integer> iterator = this.theSequence
				.getSequenceIterator();

		while (iterator.hasNext()) {
			this.currentPage = iterator.next();
			this.DoPageAccess(this.currentPage);
		}
	}

	public final int SetNumFrames(int num) {
		this.myFrames.SetNumFrames(num);
		return this.myFrames.Size();
	}
}
