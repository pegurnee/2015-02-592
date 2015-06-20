package com.egurnee.school.os.p5;

import java.util.ArrayList;

public class ProjectFiveDriver {
	public static void main(String[] args) {
		final ArrayList<PageSeq> triesFromFile = PageSequenceFactory
				.getTriesFromFile("sample_input.txt");

		final PagingManager pagingManager = new PagingManager(triesFromFile);

		// pagingManager.printAll(4);
	}

}
