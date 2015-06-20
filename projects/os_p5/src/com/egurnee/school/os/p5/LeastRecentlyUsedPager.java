package com.egurnee.school.os.p5;

import java.util.HashMap;

public class LeastRecentlyUsedPager extends AbstractPager {

	private final HashMap<Integer, Integer> recentUse;

	public LeastRecentlyUsedPager() {
		super(PagingScheme.LEAST_RECENTLY_USED);
		this.recentUse = new HashMap<Integer, Integer>();
	}

	@Override
	public void DoPageAccess(int pagePos) {
		// TODO Auto-generated method stub
		if (!this.myFrames.contains(pagePos)) {
			this.DoPageFault();
		}
		this.accessPage();
		this.recentUse.put(this.currentPage, this.myAccesses);
	}

	@Override
	public int DoPageFault() {
		// TODO Auto-generated method stub
		this.faultPage();
		int[] temp = new int[this.myFrames.Size()];
		for (int i = 0; i < this.myFrames.Size(); i++) {
			final Integer integer = this.recentUse.get(this.myFrames.get(i));
			temp[i] = integer == null ? -1 : integer;
		}

		int largest = 0;
		for (int i = 1; i < temp.length; i++) {
			if (temp[largest] > temp[i]) {
				largest = i;
			}
		}

		return this.myFrames.set(largest, this.currentPage);
	}

}
