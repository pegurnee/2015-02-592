package com.egurnee.school.os.p5;

/*

 CLASS: PrintBuffer
 by Matthew Evett, 1999

 To get the output formatted correctly, we have to buffer the current status of
 the frames at each page access.

 void Store(const Frames &f, int pageID)
 Store the given set of frames as well as the pageID in the print buffer.
 void Store(int pageID)
 Store the given pageID in the print buffer (there is no page fault, so
 the frames should remain unchanged.)
 void Print()
 Print the page fault sequence table.  Output format looks like:

 1  2  3  4  3  4  2  3  5  6  4  2  1  2
 __________________________________________
 1  1  1  4              4  4     2  2
 0  2  2  2              5  5     5  1
 0  0  3  3              3  6     6  6

 where the top line is the reference string, and the N lines below it (corresponding
 to N frames in the simulation) show the contents of the frames after each page
 request in the reference string is handled.

 */
public class PrintBuffer {
	private static final int COLS_PER_LINE = 0;

	private Frames[] myFrameHistory;
	private int[] myReferenceString;
	private int mySize;
	private boolean[] myWasPageFault;

	public PrintBuffer() {
		this.mySize = 0;
	}

	public void Print() {}

	public void Reset() {
		this.mySize = 0;
	}

	public void Store(Frames f, int pageID) {}

	public void Store(int pageID) {}

	private void PrintLine(int start, int end) {}

}
