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

#ifndef pager_h
#define pager_h

#include "stdafx.h"

#include "pageSeq.h"
#include "frames.h"
#include "printBuffer.h"

class Pager 
{
public:
  Pager(int numFrames): myNumFaults(0) {SetNumFrames(numFrames);};	// Constructor
  Pager(): myNumFaults(0) {};

  void Run(const PageSeq &seq); // Run the simultion & collect stats
  void Print(); // Print the fault table resulting from most recent Run
  int SetNumFrames(int num)
  {
    myFrames.SetNumFrames(num);
    return myFrames.Size();
  };
  int NumFaults()  {return myNumFaults;};
  int Accesses() {return myAccesses;};
  void PrintStats(int optimal);   // Print one line of final results table of running the pager.

    /* Each pager must provide the following interface: */
  virtual char * Name() = 0;   // The name of the pager   
  virtual int DoPageFault() = 0; // Handle a page fault.  Return a frame index.
  virtual void DoPageAccess(int pagePos) = 0; // Handle a page access

protected:
  int myNumFaults;                // Number of faults already handled
  int myAccesses;              // # of page accesses handled so far
  Frames myFrames;                // Current contents of frames (i.e., which pages are resident)
  PrintBuffer myHistory;          // Printing information of self.
};

#endif
