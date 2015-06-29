package os.proj.p6.allocator;

import os.proj.p6.io.api.RequestResult;
import os.proj.p6.io.api.ValidDiskRequest;

public class IndexedAllocator extends DiskDriveAllocator {
	public IndexedAllocator(int numBlocks) {
		super(numBlocks);
		this.type = AllocationType.INDEXED;
	}

	@Override
	protected RequestResult add(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RequestResult append(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RequestResult delete(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RequestResult read(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
