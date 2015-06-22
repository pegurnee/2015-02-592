package hw_9_10;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SSTFAlgorithm implements SchedulerAlgorithm {
	private final SchedulerType type = SchedulerType.SSTF;

	@Override
	public AlgorithmResult runWith(LinkedList<Integer> fifoOrder,
			int startPosition, int previousPosition, int numCylinders) {
		int currentPosition = startPosition;
		int runningDistance = 0;
		LinkedList<Integer> result = new LinkedList<>();

		while (!fifoOrder.isEmpty()) {
			final int finalCurrentPos = currentPosition;
			LinkedList<Integer> temp = new LinkedList<Integer>(fifoOrder
					.stream()
					.map(i -> i = Math.abs(finalCurrentPos - i.intValue()))
					.collect(Collectors.toList()));

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
			int location = temp.indexOf(temp.stream()
					.min(Comparator.naturalOrder()).get());

			final Integer request = fifoOrder.remove(location);
			runningDistance += Math.abs(currentPosition - request);
			currentPosition = request;
			result.add(request);
		}

		return new AlgorithmResult(result, runningDistance, this.type);
	}

}
