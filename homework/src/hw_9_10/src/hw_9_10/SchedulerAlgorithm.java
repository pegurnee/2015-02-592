package hw_9_10;

import java.util.LinkedList;

public interface SchedulerAlgorithm {
	AlgorithmResult runWith(LinkedList<Integer> fifoOrder, int startPosition,
			int numCyinders);

}
