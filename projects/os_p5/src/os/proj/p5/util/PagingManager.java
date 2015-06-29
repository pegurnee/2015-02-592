package os.proj.p5.util;

import java.util.ArrayList;

import os.proj.p5.pagers.AbstractPager;
import os.proj.p5.pagers.PagerFactory;
import os.proj.p5.pagers.PagingScheme;
import os.proj.p5.sequences.PageSeq;

public class PagingManager {
	private final AbstractPager[] pagers;
	private ArrayList<PageSeq> tries;

	public PagingManager(ArrayList<PageSeq> tries) {
		this();
		this.applyPageTries(tries);
	}

	private PagingManager() {
		final PagingScheme[] schemes = PagingScheme.values();
		final int numSchemes = schemes.length;

		this.pagers = new AbstractPager[numSchemes];
		for (int i = 0; i < numSchemes; i++) {
			this.pagers[i] = PagerFactory.createPagerWithScheme(schemes[i]);
		}
	}

	public void applyPageTries(ArrayList<PageSeq> tries) {
		this.tries = tries;
		for (PageSeq pageSeq : this.tries) {
			for (AbstractPager abstractPager : this.pagers) {
				abstractPager.Run(pageSeq);
			}
			this.printAll(pageSeq.getFramesOfMemory());
		}
	}

	public void printAll(int frames) {
		for (AbstractPager abstractPager : this.pagers) {
			abstractPager.Print();
		}
		System.out.printf("Using %d frames, the reference string yielded:%n",
				frames);

		int optimal = 0;
		for (AbstractPager abstractPager : this.pagers) {
			if (abstractPager.getTheScheme().equals(PagingScheme.OPTIMAL)) {
				optimal = abstractPager.NumFaults();
				break;
			}
		}

		for (AbstractPager abstractPager : this.pagers) {
			abstractPager.PrintStats(optimal);
		}
	}
}
