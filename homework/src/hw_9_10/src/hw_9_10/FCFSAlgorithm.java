package hw_9_10;

import java.util.Iterator;
import java.util.LinkedList;

public class FCFSAlgorithm implements SchedulerAlgorithm {

	@Override
	public AlgorithmResult runWith(LinkedList<Integer> fifoOrder,
			int startPosition, int previousPosition, int numCyinders) {
		int currentPosition = startPosition;
		int runningDistance = 0;
		LinkedList<Integer> result = new LinkedList<>();

		final Iterator<Integer> it = fifoOrder.iterator();
		while (it.hasNext()) {
			int request = it.next();
			runningDistance += Math.abs(currentPosition - request);
			result.add(request);
			currentPosition = request;
		}
		return new AlgorithmResult(result, runningDistance);
	}

}
