
# Project 4: Virtual Memory
*Due: Wednesday, April 7*


**There should be a standard method of file input stated here!!!**

In this assignment you will test the efficacy of various virtual memory paging schemes. You will implement four different paging schemes: *first-in-first-out*, *least-recently used*, *least-frequently used* (using a definition specified below), and *optimal*. Your program should read the sequence of page accesses from the file "**pages.dat**", emulate the four different paging schemes, and then print a short report detailing the relative performance statistics.
 
### Input Format  
The file is comprised of a sequence of "tries".  Each try consists of a line containing a single integer, *f*, representing the number of frames of memory in the operating system, and then, in subsequent lines, any number of integers, 0-99, representing page numbers.  The sequence of integers is a "reference string" -- a sequence of page requests (see pp. 361-2 in Silberschatz).  The reference string shall be terminated by a value of -1 (which does not represent a page number, obviously, it's just a delimiter.)
 

    Sample Input:
    3
    1 2 3 4 3 4
    2 3 5 6 4 2 1
    2 -1
    4
    1 2 3 4 2 7 5 1
    1 6 4 7 2 1
    2 5 -1

### Output  
For each "try" your program should print a table detailing the page replacements effected by each of the four paging methods, similar to Figures 9.12 and 9.14 in the textbook.  Be sure that you properly handle the printing of these tables when the length of the reference string is large enough that the table is wider than the page.  Output should never produce lines longer than 80 characters. Failure to deal with longer reference strings properly will result in at least a one third grade decrement. After printing the paging tables, your program should print a table detailing the number of page faults effected by each of the paging schemes, and the degree of optimality that represents.  Below is the output that should result from the sample input.
 
```
Example output:

FIFO:
1  2  3  4  3  4  2  3  5  6  4  2  1  2
__________________________________________
1  1  1  4              4  4     2  2
0  2  2  2              5  5     5  1
0  0  3  3              3  6     6  6

LRU:
1  2  3  4  3  4  2  3  5  6  4  2  1  2
__________________________________________
1  1  1  4              5  5  5  2  2
0  2  2  2              2  6  6  6  1
0  0  3  3              3  3  4  4  4

LFU:
1  2  3  4  3  4  2  3  5  6  4  2  1  2
__________________________________________
1  1  1  4              4  6  6  6  1
0  2  2  2              5  5  5  2  2
0  0  3  3              3  3  4  4  4

Optimal:
1  2  3  4  3  4  2  3  5  6  4  2  1  2
__________________________________________
1  1  1  4              4  4        1
0  2  2  2              2  2        2
0  0  3  3              5  6        6

Using 3 frames, the reference string yielded:
Scheme  #Faults %Optimal
FIFO    8       114.3%
LRU     9       128.6%
LFU     9       128.6%
Optimal 7       100%
```  
     
     
     
```
FIFO:
1  2  3  4  2  7  5  1  1  6  4  7  2  1  2  5
________________________________________________
1  1  1  1     7  7  7     7  4  4  4  4     5
0  2  2  2     2  5  5     5  5  7  7  7     7
0  0  3  3     3  3  1     1  1  1  2  2     2
0  0  0  4     4  4  4     6  6  6  6  1     1

LRU:
1  2  3  4  2  7  5  1  1  6  4  7  2  1  2  5
________________________________________________
1  1  1  1     7  7  7     7  4  4  4  4     5
0  2  2  2     2  2  2     6  6  6  6  1     1
0  0  3  3     3  5  5     5  5  7  7  7     7
0  0  0  4     4  4  1     1  1  1  2  2     2

LFU:
1  2  3  4  2  7  5  1  1  6  4  7  2  1  2  5
________________________________________________
1  1  1  1     7  7  7     6  6  6  2        2
0  2  2  2     2  2  2     2  4  4  4        5
0  0  3  3     3  5  5     5  5  7  7        7
0  0  0  4     4  4  1     1  1  1  1        1

Optimal:
1  2  3  4  2  7  5  1  1  6  4  7  2  1  2  5
________________________________________________
1  1  1  1     1  1        1        1        5
0  2  2  2     2  5        6        2        2
0  0  3  4     4  4        4        4        4
0  0  0  0     7  7        7        7        7

Using 4 frames, the reference string yielded:
Scheme  #Faults %Optimal
FIFO    13      144.4%
LRU     13      144.4%
LFU     12      133.3%
Optimal 9       100%
```

     

There is an ambiguity under the optimal strategy, above; note that page 4 replaces page 3 (in the third frame). This is because page 3 does not appear again in the reference string and so can be replaced. Alternatively, you could choose to place page 4 in the last frame (which is empty at that point). This would result in the same number of page faults. If you want to duplicate the output from above, you should always choose to replace that frame which will not be access for the longest period of time. If there is more than one such frame, choose that frame with the smallest index -- with four frames, frame 0 is the topmost in the diagram, frame 3 is the bottom-most.

Here is an example of the output from a longer reference string (1, 2, 3, 4, 2, 2, 1....) that would result in output lines longer than 80 characters. Notice that the tables are "split" at 78 characters (3 characters per element of the reference string).

Example of output where the reference string is long enough that it would result in output being longer than 80 columns. Here the program splits the output tables into the first 78 columns (3 chars per element of the reference string) and then 48 columns.

![image][1]

### HINTS and NOTES

My definition of "Least Frequenty Used" differs some from that of the textbook. I calculate a true frequency for each page, which is reset to 1.0 whenever a page is loaded into a frame. As an example, suppose we had 4 frames and the reference string:

`1 2 3 4 3 3 2 5`

When the 5 is encountered there is a page fault. 
The frequencies at that time should be:

    Calculating the access frequency of pages in frames.
    ____________________________________________________________________________
    |      | #accesses to the page    | # of accesses to any pages  |           |
    | PAGE | since it was last loaded | since this page was         | Frequency |
    |      | into a frame             | loaded into a frame         |           |
    ____________________________________________________________________________
    | 1    |	1                     |	7                           |  1/7=.14  |
    | 2    |	2                     |	6                           |  2/6=.33  |
    | 3    |	3                     |	5                           |  3/5=.60  |
    | 4    |	1                     |	4                           |  1/4=.24  |
    ____________________________________________________________________________
    
So page #1 has the smallest frequency and should be swapped out so that page #5 can be swapped in. Note that this is **not** the same as just counting the number of accesses (i.e., using the first column in the above table). This is to prevent a page from locking in a frame merely because at one time it was access very frequently. For example, suppose a page, P, is swapped in and then accessed many, many times. It's count (the first column, above) would be very high. Suppose, though, that the program were to continue to run for a long time, with no further accesses to P. If we were just to use the "access count" to determine which page to swap out, P would not be chosen. If, however, we use a true frequency (such as in the third column in the table above), then eventually the frequency would become low enough that it would be chosen for replacement.

#### More hints....

[This link takes you to a brief tutorial][2] on reading input text files in Java.

You should spend a fair amount of time designing your solution; don't just start writing code.  Think about what kind of classes you'll want, and what each will do. Your mainline should be relatively short: create a few objects and invoke a few methods (maybe only one?) on them. I designed a base class for all my pagers called Pager. Each of the four paging types has a class of its own, inheriting from Pager. [Here is a C++ header file for a class Pager][3], which is much like the Java class you might want to implement.

To generate the page fault tables you'll need a structure (a class object) to hold the fault information as it is generated, so that you can print it in the correct format at the appropriate time. I called this class PrintBuffer. [Here is a C++ header file for PrintBuffer][4] (you should be able figure out how to convert it to Java.) 

[1]: project_5.jpg
[2]: http://www.emunix.emich.edu/~evett/OS/inputFiles_inJava.htm
[3]: http://www.emunix.emich.edu/~evett/OS/pager.h
[4]: http://www.emunix.emich.edu/~evett/OS/printBuffer.h
