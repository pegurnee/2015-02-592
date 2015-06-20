package com.egurnee.school.os.p5;

public class FirstInFirstOutPager extends AbstractPager {
	private int framePtr;

	public FirstInFirstOutPager() {
		super(PagingScheme.FIRST_IN_FIRST_OUT);
		this.framePtr = 0;
	}

	@Override
	public void DoPageAccess(int pagePos) {
		// TODO Auto-generated method stub
		if (!this.myFrames.contains(pagePos)) {
			this.DoPageFault();
		}
		this.accessPage();
	}

	@Override
	public int DoPageFault() {
		// TODO Auto-generated method stub
		this.faultPage();
		int temp = this.myFrames.set(this.framePtr, this.currentPage);
		this.framePtr = (this.framePtr + 1) % this.myFrames.Size();
		return temp;
	}
}
