package com.egurnee.school.os.p1.results;

import com.egurnee.school.os.p1.WorkerType;

public class ResultItem {
	private final WorkerType type;
	private final double value;

	public ResultItem(WorkerType type, double value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return this.type + ": " + this.value;
	}

}
