package com.egurnee.os.p1;

public class ResultItem {
	private final WorkerType type;
	private final double value;

	public ResultItem(WorkerType type, double value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "ResultItem [type=" + this.type + ", value=" + this.value + "]";
	}

}
