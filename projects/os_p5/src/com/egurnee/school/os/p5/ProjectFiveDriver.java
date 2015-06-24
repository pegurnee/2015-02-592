package com.egurnee.school.os.p5;

import java.util.ArrayList;

public class ProjectFiveDriver {
	public static void main(String[] args) {
		final ArrayList<PageSeq> triesFromFile = PageSequenceFactory
				.getTriesFromFile("sample_input.txt");
		final ArrayList<PageSeq> triesFromFile2 = PageSequenceFactory
				.getTriesFromFile("homework_input.txt");
		final ArrayList<PageSeq> triesFromFile3 = PageSequenceFactory
				.getTriesFromFile("test_input.txt");

		final PagingManager pagingManager = new PagingManager(triesFromFile3);

		// pagingManager.printAll(4);
	}

}
