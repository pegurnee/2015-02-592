package hw_9_10;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SCANAlgorithm implements SchedulerAlgorithm {
	private final SchedulerType type = SchedulerType.SCAN;

	@Override
	public AlgorithmResult runWith(LinkedList<Integer> fifoOrder,
			int startPosition, int previousPosition, int numCylinders) {

		boolean down = Integer.compare(previousPosition, startPosition) > 0;
		int currentPosition = startPosition;
		int runningDistance = 0;
		LinkedList<Integer> result = new LinkedList<>();

		while (!fifoOrder.isEmpty()) {
			LinkedList<Integer> temp;
			int value = -1;
			final int finalCurrentPos = currentPosition;
			if (down) {
				temp = new LinkedList<Integer>(fifoOrder.stream()
						.filter(i -> i < finalCurrentPos)
						.collect(Collectors.toList()));
				value = temp.stream().max(Comparator.naturalOrder()).get();
			} else {
				temp = new LinkedList<Integer>(fifoOrder.stream()
						.filter(i -> i > finalCurrentPos)
						.collect(Collectors.toList()));
				value = temp.stream().min(Comparator.naturalOrder()).get();
			}

			final Integer request = fifoOrder.remove(fifoOrder.indexOf(value));
			runningDistance += Math.abs(currentPosition - request);
			currentPosition = request;
			result.add(request);

			final int currentPosition2 = currentPosition;
			final boolean headingDownAndNothingLower = down
														&& !(fifoOrder.stream()
																.anyMatch(i -> i < currentPosition2));
			final boolean headingUpAndNothingHigher = !down
														&& !(fifoOrder.stream()
																.anyMatch(i -> i > currentPosition2));

			if (headingDownAndNothingLower || headingUpAndNothingHigher) {
				if ((currentPosition2 == 0)
						|| (currentPosition2 == (numCylinders - 1))
					|| fifoOrder.isEmpty()) {
					down = !down;
				} else {
					if (down) {
						fifoOrder.add(0);
					} else {
						fifoOrder.add(numCylinders - 1);
					}
				}
			}

		}
		return new AlgorithmResult(result, runningDistance, this.type);
	}
}
