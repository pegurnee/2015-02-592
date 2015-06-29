package os.proj.p5.pagers;

import java.util.Iterator;
import java.util.LinkedList;

import os.proj.p5.io.PrintBuffer;
import os.proj.p5.sequences.PageSeq;

public class OptimalPager extends AbstractPager {
	private LinkedList<Integer> theSequenceList;

	public OptimalPager() {
		super(PagingScheme.OPTIMAL);
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
		this.faultPage();
		int empty = -1;
		int[] temp = new int[this.myFrames.Size()];
		for (int i = 0; i < this.myFrames.Size(); i++) {
			temp[i] = this.theSequenceList.indexOf(this.myFrames.get(i));
			if (temp[i] < 0) {
				empty = i;
				break;
			}
		}

		int largest;
		if (empty < 0) {
			largest = 0;
			for (int i = 1; i < temp.length; i++) {
				if (temp[largest] < temp[i]) {
					largest = i;
				}
			}
		} else {
			largest = empty;
		}

		return this.myFrames.set(largest, this.currentPage);
	}

	@Override
	public void Run(PageSeq seq) {
		this.theSequenceList = new LinkedList<>(seq.getTheSequence());

		this.myHistory = new PrintBuffer(seq.getFramesOfMemory());
		this.theSequence = seq;
		this.myNumFaults = 0;
		this.myAccesses = 0;

		this.SetNumFrames(seq.getFramesOfMemory());

		final Iterator<Integer> iterator = this.theSequenceList.iterator();

		while (iterator.hasNext()) {
			this.currentPage = iterator.next();
			this.DoPageAccess(this.currentPage);
			iterator.remove();
		}
	}

}
