Last Project: Disk Space Allocation

Last modified: "June 18, 2015 10:02:49 by evett"


In this assignment you will test the efficacy of two disk-allocation schemes:  Contiguous allocation and indexed allocation.  Your program takes as input a file (disk.dat) containing a list of requests to add, read, and delete files.  The first line of the input file, disk.dat, contains a single integer, the number of blocks, b, on a simulated disk drive (the blocks are numbered 0 to b-1).  Subsequent lines are requests to either add a new file of a given size, to delete a previously added file, or to print the current contents of the simulated drive.

Each "add" request is of the form: 
  add "nameString" size 
Where size  is an integer, equal to the number of blocks needed by the file named nameString (which may contain space characters, by the way).  If there is sufficient space to hold the file, it should be allocated by adding the corresponding entry to the drive's directory (which your program will have to maintain, of course).  If there is insufficient space, print an error message, and do not alter the directory.

Each"del" request is of the form: 
  del "nameString" 
Where nameString is the name of a file to be removed from the simulated drive.  If no such file exists (perhaps because there was insufficient space to add it, earlier), print an error message to that effect.  Otherwise, delete the file, freeing the blocks it occupies for use by a subsequent add request.

Each "read" request is of the form: 
  read "nameString" 
Where nameString is the name of a file to be read from the simulated drive.  Count the number of head moves necessary to accomplish the read.  One move is necessary to get to the first block of the file. (Which will be a FAT block for the indexing scheme.)  Each subsequent block will require another head move, unless the blocks are contiguous.  (I.e., the second block has an index exactly one larger than the first.)

Each "print" request is for the form: 
  print 
This should print the current drive contents in the following format (where blockij corresponds to the jth block belonging to file number i):

1. filename1 block11, block12, block13, ... , block1N 
2. filename2 block21, block22, block23, ... , block2N 
...

*  *  2  2  2  *  1  4  1  * 
1  1  1  *  *  *  *  *  *  * 
... 
 

The first part of the output is a numbered list, enumerating the files, and indicating the blocks occupied by each file.  The blocks should be printed in the order in which they appear within the file 
The second part of the output is a tabular representation of the simulated hard disk. Each line (row) of the table represents the status of 10 blocks of the disk. The first line represents the status of the blocks number 0 to 9. The second corresponds to blocks 10 to 19, and so on.

Each element of the table represents one block of the disk. The elements are either an '*', indicating that the block is unallocated, or an integer, i, indicating that this block is being used to store a portion of file number i, corresponding to the ith entry in the numbered list, above.

Here is a sample input file, and the corresponding output file.

Details of the allocation methods

Define a hole to be a maximal contiguous sequence of unallocated blocks. ("Maximal" means that the block, if any, immediately preceding and following the hole must be allocated.) The number of blocks comprising a hole is the size of the hole.
For contiguous allocation, use a best-fit methodology. For a file consisting of n blocks, allocate n contiguous blocks within the smallest hole whose size is greater than or equal to n. The allocated blocks should be the smallest numbered blocks in the hole. (I.e., allocate blocks from the top, within each hole.)

For indexed allocation, assume that you can store the addresses (indexes, really) of 8 blocks in a FAT block. If the 8th address is used, then, it must be the address of another FAT block. For example, if a file required 18 blocks to hold its content, it would actually require 21 blocks to store the file: 18 for data, plus 3 FAT blocks (7 data block addresses in the first two FAT blocks, and 4 in the last).

In allocating blocks, always allocate "top-down": the first block (which will be a FAT block) will be the lowest numbered free block.

Counting Head Moves

You are required to count the number of head moves the disk head of the simulated hard disk would have to make to satisfy the file access commands specified in disk.dat. For simplicity, you need only keep track of head moves for the read operation.
Notes:

You'll have to maintain a free list of some kind. The book outlines a couple of methods, including bit vectors, tables, and linked lists. I leave the design of the free list up to you.
OUTPUT:

Run your simulation on the input file, disk.dat, first using contiguous allocation.  Then run the simulation on the same input, using indexed allocation.  After each run, print the number of head moves required during the simulation, and the number of files that could not be allocated because there was insufficient free space. (We're primarily interested in the problem of external fragmentation, here.)

EXTRA CREDIT:

For an extra .5 grade-point on this assignment, implement an append command. (If you implement the extra credit, be sure to indicate that you have done so in your comment for the dropbox submission.) Each append request is of the form: 
  append "nameString" size 
Where size  is an integer, equal to the number of extra blocks needed by the file named nameString. The nameString may contain space characters, which is why the quote characters are part of the syntax of the request. Java's StringTokenizer class may be useful here, but there are many ways to handle the quote characters.

Note that with contiguous allocation, an append may necessitate moving the entire contents of the file to a new hole that is large enough to store the expanded file. Use best-fit, again, to determine the new location. If a file must be moved, increment the head moves counter by one for each block in the file to be copied.

If there is insufficient space, print an error message, and do not alter the current disk set-up. If there is no file with the given name, print an appropriate warning, and create a new file with that name.