# Sample Input/Output for Scheduler With IO

The bottom of this page shows the output that might be generated for something this input file:

```
1   0 180 180 220
2  70 150 130 220
3 260 180 130 180  70  70
```

Here's the output:

```
kernel blockingTilThere's a job
Thread[Submittor,7,main] placing Thread[Job1,7,main] on readyQ
evidently there is now a job on readyQ
filler MJW0 is completing its task #0
filler MJW0 is completing its task #1
filler MJW0 is completing its task #2
filler MJW0 is completing its task #3
filler MJW0 is completing its task #4
filler MJW0 is completing its task #5
filler MJW0 is completing its task #6
Thread[Submittor,7,main] placing Thread[Job2,7,main] on readyQ
filler MJW0 is completing its task #7
filler MJW0 is completing its task #8
filler MJW0 is completing its task #9
filler MJW0 is completing its task #10
filler MJW0 is completing its task #11
filler MJW0 is completing its task #12
filler MJW0 is completing its task #13
filler MJW0 is completing its task #14
filler MJW0 is completing its task #15
filler MJW0 is completing its task #16
filler MJW1 is completing its task #0
filler MJW1 is completing its task #1
filler MJW1 is completing its task #2
filler MJW1 is completing its task #3
filler MJW1 is completing its task #4
filler MJW1 is completing its task #5
filler MJW1 is completing its task #6
filler MJW1 is completing its task #7
filler MJW1 is completing its task #8
filler MJW1 is completing its task #9
filler MJW1 is completing its task #10
filler MJW1 is completing its task #11
filler MJW1 is completing its task #12
filler MJW1 is completing its task #13
Thread[Submittor,7,main] placing Thread[Job3,7,main] on readyQ
filler MJW2 is completing its task #0
filler MJW2 is completing its task #1
filler MJW2 is completing its task #2
Thread[IOTimer for Job1,7,main] placing Thread[Job1,7,main] on readyQ
filler MJW2 is completing its task #3
filler MJW2 is completing its task #4
filler MJW2 is completing its task #5
filler MJW2 is completing its task #6
filler MJW2 is completing its task #7
filler MJW2 is completing its task #8
filler MJW2 is completing its task #9
filler MJW2 is completing its task #10
filler MJW2 is completing its task #11
filler MJW2 is completing its task #12
Thread[IOTimer for Job2,7,main] placing Thread[Job2,7,main] on readyQ
filler MJW2 is completing its task #13
filler MJW2 is completing its task #14
filler MJW2 is completing its task #15
filler MJW2 is completing its task #16
filler MJW0 is completing its task #17
filler MJW0 is completing its task #18
filler MJW0 is completing its task #19
filler MJW0 is completing its task #20
filler MJW0 is completing its task #21
filler MJW0 is completing its task #22
filler MJW0 is completing its task #23
filler MJW0 is completing its task #24
filler MJW0 is completing its task #25
filler MJW0 is completing its task #26
filler MJW0 is completing its task #27
filler MJW0 is completing its task #28
filler MJW0 is completing its task #29
Thread[IOTimer for Job3,7,main] placing Thread[Job3,7,main] on readyQ
filler MJW0 is completing its task #30
filler MJW0 is completing its task #31
filler MJW0 is completing its task #32
filler MJW0 is completing its task #33
filler MJW0 is completing its task #34
filler MJW0 is completing its task #35
filler MJW0 is completing its task #36
filler MJW0 is completing its task #37
filler MJW1 is completing its task #14
filler MJW1 is completing its task #15
filler MJW1 is completing its task #16
filler MJW1 is completing its task #17
filler MJW1 is completing its task #18
filler MJW1 is completing its task #19
filler MJW1 is completing its task #20
filler MJW1 is completing its task #21
filler MJW1 is completing its task #22
filler MJW1 is completing its task #23
filler MJW1 is completing its task #24
filler MJW1 is completing its task #25
filler MJW1 is completing its task #26
filler MJW1 is completing its task #27
filler MJW1 is completing its task #28
filler MJW1 is completing its task #29
filler MJW1 is completing its task #30
filler MJW1 is completing its task #31
filler MJW1 is completing its task #32
filler MJW1 is completing its task #33
filler MJW1 is completing its task #34
filler MJW2 is completing its task #17
filler MJW2 is completing its task #18
filler MJW2 is completing its task #19
filler MJW2 is completing its task #20
filler MJW2 is completing its task #21
filler MJW2 is completing its task #22
filler MJW2 is completing its task #23
filler MJW2 is completing its task #24
filler MJW2 is completing its task #25
filler MJW2 is completing its task #26
filler MJW2 is completing its task #27
filler MJW2 is completing its task #28
filler MJW2 is completing its task #29
filler MJW2 is completing its task #30
filler MJW2 is completing its task #31
filler MJW2 is completing its task #32
filler MJW2 is completing its task #33
kernel blockingTilThere's a job
Thread[IOTimer for Job3,7,main] placing Thread[Job3,7,main] on readyQ
evidently there is now a job on readyQ
filler MJW2 is completing its task #34
filler MJW2 is completing its task #35
filler MJW2 is completing its task #36
filler MJW2 is completing its task #37
filler MJW2 is completing its task #38
filler MJW2 is completing its task #39
filler MJW2 is completing its task #40

Wall time at start: 1434330091639
BurstStart BurstEnd JOB 
         0        1 IDLE 
         2      187 Job1--CPU_Burst before IO burst
       188      338 Job2--CPU_Burst before IO burst
       339      519 Job3--CPU_Burst before IO burst
       519      744 Job1 final CPU burst
       744      969 Job2 final CPU burst
       969     1153 Job3--CPU_Burst before IO burst
      1153     1225 IDLE 
      1225     1300 Job3 final CPU burst
      1300     1300 FINISHED
```

 