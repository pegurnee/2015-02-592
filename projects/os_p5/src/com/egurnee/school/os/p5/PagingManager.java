package com.egurnee.school.os.p5;

import java.util.ArrayList;

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
			for (int i = 0; i < this.pagers.length; i++) {
				this.pagers[i].Run(pageSeq);
			}
		}
	}

	public void printAll() {
		for (AbstractPager abstractPager : this.pagers) {
			abstractPager.Print();
		}
	}
}
