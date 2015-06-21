package hw_9_10;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class SCANAlgorithm implements SchedulerAlgorithm {

	@Override
	public AlgorithmResult runWith(LinkedList<Integer> fifoOrder,
			int startPosition, int previousPosition, int numCyinders) {
		// TODO Auto-generated method stub

		boolean down = Integer.compare(previousPosition, startPosition) > 0;
		int currentPosition = startPosition;

		while (!fifoOrder.isEmpty()) {
			LinkedList<Integer> temp;
			final int finalCurrentPos = currentPosition;
			if (down) {
				temp = new LinkedList<Integer>(fifoOrder.stream()
						.filter(i -> i < currentPosition)
						.collect(Collectors.toList()));

			} else {
				temp = new LinkedList<Integer>(fifoOrder.stream()
						.filter(i -> i > currentPosition)
						.collect(Collectors.toList()));
			}

		}
		return null;
	}
}
