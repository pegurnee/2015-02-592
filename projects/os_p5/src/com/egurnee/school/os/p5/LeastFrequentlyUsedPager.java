package com.egurnee.school.os.p5;

import java.util.HashMap;

public class LeastFrequentlyUsedPager extends AbstractPager {
	private final HashMap<Integer, Integer> anyUse;
	private final HashMap<Integer, Integer> frequentUse;

	public LeastFrequentlyUsedPager() {
		super(PagingScheme.LEAST_FREQUENTLY_USED);
		this.frequentUse = new HashMap<Integer, Integer>();
		this.anyUse = new HashMap<Integer, Integer>();
	}

	@Override
	public void DoPageAccess(int pagePos) {
		// TODO Auto-generated method stub
		if (!this.myFrames.contains(pagePos)) {
			this.DoPageFault();
		}
		this.accessPage();

		final Integer integer = this.frequentUse.get(this.currentPage);
		this.frequentUse.put(this.currentPage,
				((integer == null) ? 0 : integer) + 1);
		for (int i = 0; i < this.myFrames.Size(); i++) {
			final int key = this.myFrames.get(i);
			final Integer integer2 = this.anyUse.get(key);
			this.anyUse.put(key, ((integer2 == null) ? 0 : integer2) + 1);

		}
	}

	@Override
	public int DoPageFault() {
		// TODO Auto-generated method stub
		this.faultPage();
		int[] temp = new int[this.myFrames.Size()];
		int[] any = new int[this.myFrames.Size()];
		for (int i = 0; i < this.myFrames.Size(); i++) {
			final Integer integer = this.frequentUse.get(this.myFrames.get(i));
			final Integer integer2 = this.anyUse.get(this.myFrames.get(i));
			temp[i] = integer == null ? -1 : integer;
			any[i] = integer2 == null ? -1 : integer2;
		}

		for (int i = 0; i < temp.length; i++) {
			if (temp[i] < 0) {
				return this.myFrames.set(i, this.currentPage);
			}
		}

		double least = Double.MIN_NORMAL;
		int loc = 0;
		for (int i = 0; i < temp.length; i++) {
			double test = temp[i] / (double) any[i];
			if (least > test) {
				least = test;
				loc = i;
			}
		}

		final int set = this.myFrames.set(loc, this.currentPage);
		this.frequentUse.put(set, 0);
		this.anyUse.put(set, 0);
		return set;
	}
}
