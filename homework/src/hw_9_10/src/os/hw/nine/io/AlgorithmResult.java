package os.hw.nine.io;

import java.util.LinkedList;

import os.hw.nine.SchedulerType;

public class AlgorithmResult {
	private LinkedList<Integer> order;
	private int totalDistance;
	private final SchedulerType type;

	public AlgorithmResult(LinkedList<Integer> order, int totalDistance,
			SchedulerType type) {
		super();
		this.order = order;
		this.totalDistance = totalDistance;
		this.type = type;
	}

	public LinkedList<Integer> getOrder() {
		return this.order;
	}

	public int getTotalDistance() {
		return this.totalDistance;
	}

	public void setOrder(LinkedList<Integer> order) {
		this.order = order;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	@Override
	public String toString() {
		return "AlgorithmResult [type=" + this.type + ", order=" + this.order
				+ ", totalDistance=" + this.totalDistance + "]";
	}

}
