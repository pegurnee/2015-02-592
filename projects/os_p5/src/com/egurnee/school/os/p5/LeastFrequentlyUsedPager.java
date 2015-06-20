package com.egurnee.school.os.p5;

public class LeastFrequentlyUsedPager extends AbstractPager {

	public static void main(String[] args) {
		new LeastFrequentlyUsedPager().PrintStats(7);
	}

	public LeastFrequentlyUsedPager() {
		this.theScheme = PagingScheme.LEAST_FREQUENTLY_USED;
	}

	@Override
	public void DoPageAccess(int pagePos) {
		// TODO Auto-generated method stub

	}

	@Override
	public int DoPageFault() {
		// TODO Auto-generated method stub
		return 0;
	}
}
