package com.egurnee.os.p1.results;

import java.util.EnumMap;

import com.egurnee.os.p1.WorkerType;

public final class ResultsSingleton {
	private static ResultsSingleton instance = new ResultsSingleton();

	public static synchronized ResultsSingleton getInstance() {
		return instance;
	}

	private final EnumMap<WorkerType, ResultItem> results;

	private ResultsSingleton() {
		this.results = new EnumMap<>(WorkerType.class);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public ResultItem get(WorkerType type) {
		return this.results.get(type);
	}

	public ResultItem put(WorkerType type, ResultItem item) {
		return this.results.put(type, item);
	}
}
