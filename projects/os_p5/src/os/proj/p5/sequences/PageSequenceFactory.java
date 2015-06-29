package os.proj.p5.sequences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PageSequenceFactory {
	public static ArrayList<PageSeq> getTriesFromFile(String filename) {
		ArrayList<PageSeq> tries = new ArrayList<>();
		Scanner input = null;

		try {
			input = new Scanner(new FileInputStream(filename));

			while (input.hasNextInt()) {
				tries.add(PageSequenceFactory.getSequence(input));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			input.close();
		}

		return tries;
	}

	private static PageSeq getSequence(Scanner input) {
		final int numFramesOfMemory = input.nextInt();
		PageSeq theSequence = new PageSeq(numFramesOfMemory);

		while (input.hasNextInt()) {
			final int nextInt = input.nextInt();
			if (nextInt == -1) {
				break;
			}
			theSequence.addPageRequest(nextInt);
		}
		return theSequence;

	}
}
