package com.egurnee.school.os.p5;

import java.util.Iterator;
import java.util.LinkedList;

public class PageSeq {

	private final int numFramesOfMemory;
	private final LinkedList<Integer> pageRequests;

	public PageSeq(int numFramesOfMemory) {
		this.numFramesOfMemory = numFramesOfMemory;
		this.pageRequests = new LinkedList<Integer>();
	}

	public void addPageRequest(int nextInt) {
		this.pageRequests.add(nextInt);
	}

	public int getFramesOfMemory() {
		return this.numFramesOfMemory;
	}

	public Iterator<Integer> getSequenceIterator() {
		return this.pageRequests.iterator();
	}
}
