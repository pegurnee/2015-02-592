package hw_9_10;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SSTFAlgorithm implements SchedulerAlgorithm {

	@Override
	public AlgorithmResult runWith(LinkedList<Integer> fifoOrder,
			int startPosition, int numCyinders) {
		// TODO Auto-generated method stub
		int currentPosition = startPosition;
		int runningDistance = 0;
		LinkedList<Integer> result = new LinkedList<>();

		while (!fifoOrder.isEmpty()) {
			final int finalCurrentPos = currentPosition;
			LinkedList<Integer> temp = new LinkedList<Integer>(fifoOrder
					.stream()
					.map(i -> i = Math.abs(finalCurrentPos - i.intValue()))
					.collect(Collectors.toList()));

			// temp;
			System.out.println(temp);
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
			// System.out.println(request);
			runningDistance += Math.abs(currentPosition - request);
			currentPosition = request;
			result.add(request);
		}

		return new AlgorithmResult(result, runningDistance);
	}

}
