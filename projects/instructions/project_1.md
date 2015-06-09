# Programming Assignment

This is a modified version of exercise 4.21 from the 9th edition of the textbook.

Write a multi-threaded program, Stats.java, in Java that calculates various statistical values
for a list of numbers. This program (think of it as the "parent" thread) will prompt the user to provide a single line of input consisting of an arbitrarily long series of numbers and will then create three separate worker threads.  One thread will determine the average of the numbers, the second
will determine the maximum value, and the third will determine the minimum value. For example, suppose your program is passed the integers

90 81 78 95 79 72 85

The program will report

    The average value is 82
	The minimum value is 72
	The maximum value is 95

The variables representing the **average**, **minimum**, and **maximum** values will be stored globally (i.e., in memory accessible by all three workers). The worker threads will set these values, and the
parent thread will output the values once the workers have provided the results of their calculations and exited (terminated). 

To solve this problem using Java you'll need at least two classes: one for the main program (the "driver"/parent thread), and one for the workers.  Or you could use a separate class for each of the workers.  If you use just one worker class, you'll probably want to pass an argument to the constructor to indicate the type of worker (average, minimum, maximum).

I should be able to test your program by invoking the main method of Stats.java.

 

### Graduate Students (extra credit for undergraduates):

Expand this program by creating additional threads that determine the **median** and **standard deviation**.