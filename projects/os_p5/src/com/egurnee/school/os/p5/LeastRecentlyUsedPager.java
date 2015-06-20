package com.egurnee.school.os.p5;

public class LeastRecentlyUsedPager extends AbstractPager {

	public LeastRecentlyUsedPager() {

		super(PagingScheme.LEAST_RECENTLY_USED);
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
