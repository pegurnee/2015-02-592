package com.egurnee.school.os.p5;

public class OptimalPager extends AbstractPager {

	public OptimalPager() {
		super(PagingScheme.OPTIMAL);
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
