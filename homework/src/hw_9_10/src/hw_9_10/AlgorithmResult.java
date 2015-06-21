package hw_9_10;

import java.util.LinkedList;

public class AlgorithmResult {
	private LinkedList<Integer> order;
	private int totalDistance;

	public AlgorithmResult(LinkedList<Integer> order, int totalDistance) {
		super();
		this.order = order;
		this.totalDistance = totalDistance;
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
		return "AlgorithmResult [order=" + this.order + ", totalDistance="
				+ this.totalDistance + "]";
	}
}
