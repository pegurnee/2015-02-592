package os.hw.nine.algorithm;

import java.util.LinkedList;

import os.hw.nine.io.AlgorithmResult;

public interface SchedulerAlgorithm {
	AlgorithmResult runWith(LinkedList<Integer> fifoOrder, int startPosition,
			int previousPosition, int numCylinders);

}
