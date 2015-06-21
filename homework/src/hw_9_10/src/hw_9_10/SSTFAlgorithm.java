package hw_9_10;

import java.util.Iterator;
import java.util.LinkedList;

public class SSTFAlgorithm implements SchedulerAlgorithm {

	@Override
	public AlgorithmResult runWith(LinkedList<Integer> fifoOrder,
			int startPosition, int numCyinders) {
		// TODO Auto-generated method stub
		int currentPosition = startPosition;
		int runningDistance = 0;
		LinkedList<Integer> result = new LinkedList<>();

		while (!fifoOrder.isEmpty()) {
			LinkedList<Integer> temp = new LinkedList<Integer>(fifoOrder);
			for (Integer integer : temp) {
				integer = Math.abs(integer - currentPosition);
			}
			int currentMin = Integer.MAX_VALUE;
			int i = 0;
			int loc = -1;
			for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
				Integer integer = (Integer) iterator.next();
				if (integer < currentMin) {
					currentMin = integer;
					loc = i;
				}
				i++;
			}

			final Integer request = fifoOrder.remove(loc);
			runningDistance += Math.abs(currentPosition - request);
			currentPosition = request;
			result.add(request);
		}

		return new AlgorithmResult(result, runningDistance);
	}

}
