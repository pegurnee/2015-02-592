package com.egurnee.school.os.p5;

public enum PagingScheme {
	FIRST_IN_FIRST_OUT("FIFO"),
	LEAST_FREQUENTLY_USED("LFU"),
	LEAST_RECENTLY_USED("LRU"),
	OPTIMAL("OPTIMAL");
	private final String name;

	private PagingScheme(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
