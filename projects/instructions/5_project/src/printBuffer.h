

#ifndef PRINT_BUFFER_H
#define PRINT_BUFFER_H

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
#include "pageSeq.h"
#include "frames.h"

class PrintBuffer
{
public:
	PrintBuffer(): mySize(0) {};
	void Reset() {mySize = 0;};
	void Store(const Frames &f, int pageID); // Store the current given set of frames
	void Store(int pageID);           // Store the given page ID
	void Print();  // Print the history to cout

private:
	int mySize; // Number of page faults yet occurred
	int myReferenceString[MAX_ENTRIES]; // The reference string (page #s).
    bool myWasPageFault[MAX_ENTRIES];   // Indicates a page fault occurred @ this point
	Frames myFrameHistory[MAX_ENTRIES];
    void PrintLine(int start, int end);  // Print part of fault table
    static const int COLS_PER_LINE;  // Number of columns of output per line
};

#endif